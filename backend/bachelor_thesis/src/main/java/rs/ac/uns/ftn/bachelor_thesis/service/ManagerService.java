package rs.ac.uns.ftn.bachelor_thesis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Manager;
import rs.ac.uns.ftn.bachelor_thesis.model.Role;
import rs.ac.uns.ftn.bachelor_thesis.repository.ManagerRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public Manager registerManager(RegisterInfoDTO dto) {
        log.info("Registering a new manager: {}", dto.getEmail());

        Manager manager = new Manager();
        manager.setFirstName(dto.getFirstName());
        manager.setLastName(dto.getLastName());
        manager.setEmail(dto.getEmail());
        manager.setPassword(passwordEncoder.encode(dto.getPassword()));
        manager.setTelephone(dto.getTelephone());

        Role roleManager = userService.getRoleByName("ROLE_MANAGER");

        if (roleManager == null) {
            return null;
        }

        manager.getRoles().add(roleManager);

        return managerRepository.save(manager);
    }
}
