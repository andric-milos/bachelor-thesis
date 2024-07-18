package rs.ac.uns.ftn.bachelor_thesis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.exception.InternalServerErrorException;
import rs.ac.uns.ftn.bachelor_thesis.exception.ManagerNotFoundException;
import rs.ac.uns.ftn.bachelor_thesis.model.Manager;
import rs.ac.uns.ftn.bachelor_thesis.model.Role;
import rs.ac.uns.ftn.bachelor_thesis.repository.ManagerRepository;
import rs.ac.uns.ftn.bachelor_thesis.repository.RoleRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    /**
     * Receives previously validated RegisterInfoDTO object, creates Manager object
     * from it and saves it in the database.
     *
     * @param dto
     * @return registered Manager object
     */
    public Manager registerManager(RegisterInfoDTO dto) {
        log.info("Registering a new manager: {}", dto.getEmail());
        Manager manager = createManagerFromRegisterInfoDTO(dto);
        return managerRepository.save(manager);
    }

    private Manager createManagerFromRegisterInfoDTO(RegisterInfoDTO dto) {
        Manager manager = new Manager();
        manager.setFirstName(dto.getFirstName());
        manager.setLastName(dto.getLastName());
        manager.setEmail(dto.getEmail());
        manager.setPassword(passwordEncoder.encode(dto.getPassword()));
        manager.setTelephone(dto.getTelephone());

        Role roleManager = roleRepository.findByName("ROLE_MANAGER").orElseThrow(
                () -> new InternalServerErrorException("Role ROLE_MANAGER doesn't exist!")
        );

        manager.getRoles().add(roleManager);

        return manager;
    }

    public Manager getManagerByEmail(String email) {
        return managerRepository.findByEmail(email).orElseThrow(() -> new ManagerNotFoundException(email));
    }
}
