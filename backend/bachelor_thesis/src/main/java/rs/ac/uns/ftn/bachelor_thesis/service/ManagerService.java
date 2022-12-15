package rs.ac.uns.ftn.bachelor_thesis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Manager;
import rs.ac.uns.ftn.bachelor_thesis.model.Role;
import rs.ac.uns.ftn.bachelor_thesis.model.User;
import rs.ac.uns.ftn.bachelor_thesis.repository.ManagerRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    /**
     * Receives previously validated RegisterInfoDTO object, creates Manager object
     * from it and saves it in the database.
     *
     * @param dto
     * @return 0 if manager is successfully registered, 1 if role is
     * not found in the database, 2 if email is already taken
     */
    public int registerManager(RegisterInfoDTO dto) {
        log.info("Registering a new manager: {}", dto.getEmail());

        Manager manager = new Manager();
        manager.setFirstName(dto.getFirstName());
        manager.setLastName(dto.getLastName());
        manager.setEmail(dto.getEmail());
        manager.setPassword(passwordEncoder.encode(dto.getPassword()));
        manager.setTelephone(dto.getTelephone());

        Optional<Role> roleManager = userService.getRoleByName("ROLE_MANAGER");
        if (roleManager.isEmpty()) {
            return 1;
        }

        Optional<User> user = userService.getUserByEmail(dto.getEmail());
        if (user.isPresent()) {
            return 2;
        }

        manager.getRoles().add(roleManager.get());
        managerRepository.save(manager);

        return 0;
    }

    public Optional<Manager> getManagerByEmail(String email) {
        return managerRepository.findByEmail(email);
    }
}
