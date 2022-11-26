package rs.ac.uns.ftn.bachelor_thesis.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.model.Role;
import rs.ac.uns.ftn.bachelor_thesis.model.User;
import rs.ac.uns.ftn.bachelor_thesis.repository.RoleRepository;
import rs.ac.uns.ftn.bachelor_thesis.repository.UserRepository;
import rs.ac.uns.ftn.bachelor_thesis.security.TokenUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtil tokenUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("Username with email " + email + " not found");
        } else {
            log.info("User with email {} found in the database.", email);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

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

        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);

        user.getRoles().add(role);

        userRepository.save(user); // this line is redundant, if we put @Transactional annotation above the class declaration
    }

    public User getUserByEmail(String email) {
        log.info("Fetching user {} from the database", email);
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        log.info("Fetching all users from the database");
        return userRepository.findAll();
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    public String renewToken(String refreshToken, String issuer) {
        DecodedJWT decodedJWT = tokenUtil.verify(refreshToken);

        String email = decodedJWT.getSubject();
        User user = getUserByEmail(email);

        return tokenUtil.generateAccessToken(
                user.getEmail(),
                issuer,
                user.getRoles().stream().map(Role::getName).collect(Collectors.toList())
        );
    }
}
