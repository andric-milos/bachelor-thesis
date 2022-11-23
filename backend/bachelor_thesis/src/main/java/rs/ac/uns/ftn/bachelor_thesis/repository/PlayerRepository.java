package rs.ac.uns.ftn.bachelor_thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByEmail(String email);
}
