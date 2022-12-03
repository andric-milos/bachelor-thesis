package rs.ac.uns.ftn.bachelor_thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.bachelor_thesis.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
