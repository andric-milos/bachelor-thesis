package rs.ac.uns.ftn.bachelor_thesis.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.LoginInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.TokensDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.UserDTO;
import rs.ac.uns.ftn.bachelor_thesis.exception.CustomizableBadRequestException;
import rs.ac.uns.ftn.bachelor_thesis.exception.UnauthorizedException;
import rs.ac.uns.ftn.bachelor_thesis.model.Role;
import rs.ac.uns.ftn.bachelor_thesis.model.User;
import rs.ac.uns.ftn.bachelor_thesis.repository.RoleRepository;
import rs.ac.uns.ftn.bachelor_thesis.repository.UserRepository;
import rs.ac.uns.ftn.bachelor_thesis.security.TokenUtil;

import java.util.*;
import java.util.stream.Collectors;

import static rs.ac.uns.ftn.bachelor_thesis.util.ValidationUtil.trimAndValidateRegisterInfo;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenUtil tokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PlayerService playerService;
    private final ManagerService managerService;

    public Optional<User> getUserByEmail(String email) {
        log.info("Fetching user {} from the database", email);
        return userRepository.findByEmail(email);
    }

    public Optional<Role> getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    public Map<String, String> renewToken(String refreshToken, String issuer) {
        if (refreshToken != null) {
            try {
                DecodedJWT decodedJWT = tokenUtil.verify(refreshToken);

                String email = decodedJWT.getSubject();
                Optional<User> user = getUserByEmail(email);

                String accessToken = tokenUtil.generateAccessToken(
                        user.get().getEmail(),
                        issuer,
                        user.get().getRoles().stream().map(Role::getName).collect(Collectors.toList())
                );

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);

                return tokens;
            } catch (Exception e) {
                e.printStackTrace();
                throw new UnauthorizedException(e.getMessage());
            }
        } else {
            throw new CustomizableBadRequestException("Refresh token is missing!");
        }
    }

    public TokensDTO login(LoginInfoDTO dto, String issuer) {
        log.info("Email: {}, Password: {}", dto.getEmail(), dto.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        String accessToken = tokenUtil.generateAccessToken(
                user.getUsername(),
                issuer,
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
        );

        String refreshToken = tokenUtil.generateRefreshToken(
                user.getUsername(),
                issuer
        );

        DecodedJWT decodedJWT = tokenUtil.verify(accessToken);
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

        return new TokensDTO(accessToken, refreshToken, dto.getEmail(), issuer, roles, decodedJWT.getExpiresAt().getTime());
    }

    public UserDTO whoAmI() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UnauthorizedException("Not logged in!")
        );

        return new UserDTO(user);
    }

    public UserDTO register(RegisterInfoDTO dto) {
        dto = trimAndValidateRegisterInfo(dto);

        if (dto == null) {
            throw new CustomizableBadRequestException("Invalid input of data!");
        }

        String email = dto.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new CustomizableBadRequestException(String.format("Email %s is already taken!", email));
        }

        // Role whitelisted values: "manager", "player"
        String role = dto.getRole();
        if (role.equals("player")) return new UserDTO(playerService.registerPlayer(dto));
        else if (role.equals("manager")) return new UserDTO(managerService.registerManager(dto));
        else throw new CustomizableBadRequestException(String.format("Role %s doesn't exist!", role));
    }
}
