package rs.ac.uns.ftn.bachelor_thesis.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.LoginInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.TokensDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Role;
import rs.ac.uns.ftn.bachelor_thesis.model.User;
import rs.ac.uns.ftn.bachelor_thesis.repository.RoleRepository;
import rs.ac.uns.ftn.bachelor_thesis.repository.UserRepository;
import rs.ac.uns.ftn.bachelor_thesis.security.TokenUtil;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtil tokenUtil;
    private final AuthenticationManager authenticationManager;

    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role {} to user {}", roleName, email);

        Optional<User> userOptional = userRepository.findByEmail(email);
        Optional<Role> roleOptional = roleRepository.findByName(roleName);

        if (userOptional.isPresent() && roleOptional.isPresent()) {
            User user = userOptional.get();
            user.getRoles().add(roleOptional.get());

            userRepository.save(user); // this line is redundant, if we put @Transactional annotation above the class declaration
            log.info("Role {} successfully added to user {}", roleName, email);
        } else {
            log.info("Either role {} or user {} not found in the database!", roleName, email);
        }
    }

    public Optional<User> getUserByEmail(String email) {
        log.info("Fetching user {} from the database", email);
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        log.info("Fetching all users from the database");
        return userRepository.findAll();
    }

    public Optional<Role> getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    public String renewToken(String refreshToken, String issuer) {
        DecodedJWT decodedJWT = tokenUtil.verify(refreshToken);

        String email = decodedJWT.getSubject();
        Optional<User> user = getUserByEmail(email);

        return tokenUtil.generateAccessToken(
                user.get().getEmail(),
                issuer,
                user.get().getRoles().stream().map(Role::getName).collect(Collectors.toList())
        );
    }

    public TokensDTO login(LoginInfoDTO dto, String issuer) {
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

        HashMap<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        DecodedJWT decodedJWT = tokenUtil.verify(accessToken);
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

        TokensDTO tokensDTO = new TokensDTO(accessToken, refreshToken, dto.getEmail(), issuer, roles, decodedJWT.getExpiresAt().getTime());

        return tokensDTO;
    }
}
