package rs.ac.uns.ftn.bachelor_thesis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;
import rs.ac.uns.ftn.bachelor_thesis.model.Role;
import rs.ac.uns.ftn.bachelor_thesis.model.User;
import rs.ac.uns.ftn.bachelor_thesis.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayerByEmail(String email) {
        return playerRepository.findByEmail(email);
    }

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    /**
     * Receives previously validated RegisterInfoDTO object, creates Player object
     * from it and saves it in the database.
     *
     * @param dto
     * @return 0 if player is successfully registered, 1 if role is
     * not found in the database, 2 if email is already taken
     */
    public int registerPlayer(RegisterInfoDTO dto) {
        log.info("Registering a new player: {}", dto.getEmail());

        Player player = new Player();
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setEmail(dto.getEmail());
        player.setPassword(passwordEncoder.encode(dto.getPassword()));
        player.setTelephone(dto.getTelephone());

        Optional<Role> rolePlayer = userService.getRoleByName("ROLE_PLAYER");
        if (rolePlayer.isEmpty()) {
            return 1;
        }

        Optional<User> user = userService.getUserByEmail(dto.getEmail());
        if (user.isPresent()) {
            return 2;
        }

        player.getRoles().add(rolePlayer.get());
        playerRepository.save(player);

        return 0;
    }
}
