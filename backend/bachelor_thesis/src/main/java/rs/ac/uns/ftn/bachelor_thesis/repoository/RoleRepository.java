package rs.ac.uns.ftn.bachelor_thesis.repoository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.bachelor_thesis.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
