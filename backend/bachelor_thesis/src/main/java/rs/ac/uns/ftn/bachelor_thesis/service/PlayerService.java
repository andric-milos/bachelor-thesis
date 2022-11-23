package rs.ac.uns.ftn.bachelor_thesis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;
import rs.ac.uns.ftn.bachelor_thesis.model.Role;
import rs.ac.uns.ftn.bachelor_thesis.repository.PlayerRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public Player registerPlayer(RegisterInfoDTO dto) {
        log.info("Registering a new player: {}", dto.getEmail());

        Player player = new Player();
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setEmail(dto.getEmail());
        player.setPassword(passwordEncoder.encode(dto.getPassword()));
        player.setTelephone(dto.getTelephone());

        Role rolePlayer = userService.getRoleByName("ROLE_PLAYER");

        if (rolePlayer == null) {
            return null;
        }

        player.getRoles().add(rolePlayer);

        return playerRepository.save(player);
    }
}
