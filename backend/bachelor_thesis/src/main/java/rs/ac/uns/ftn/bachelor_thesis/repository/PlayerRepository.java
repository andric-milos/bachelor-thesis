package rs.ac.uns.ftn.bachelor_thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByEmail(String email);
}
