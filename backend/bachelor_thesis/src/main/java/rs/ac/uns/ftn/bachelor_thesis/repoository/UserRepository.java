package rs.ac.uns.ftn.bachelor_thesis.repoository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.bachelor_thesis.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
