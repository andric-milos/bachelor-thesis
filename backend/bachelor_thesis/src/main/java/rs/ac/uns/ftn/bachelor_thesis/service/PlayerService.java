package rs.ac.uns.ftn.bachelor_thesis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.PlayerDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.PlayerWithStatisticsDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.RegisterInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.exception.InternalServerErrorException;
import rs.ac.uns.ftn.bachelor_thesis.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;
import rs.ac.uns.ftn.bachelor_thesis.model.Role;
import rs.ac.uns.ftn.bachelor_thesis.repository.PlayerRepository;
import rs.ac.uns.ftn.bachelor_thesis.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public List<PlayerDTO> getAllPlayers() {
        List<PlayerDTO> playersDto = new ArrayList<>();

        List<Player> players = playerRepository.findAll();
        players.forEach(player -> {
            playersDto.add(new PlayerDTO(player));
        });

        return playersDto;
    }

    public Optional<Player> getPlayerByEmail(String email) {
        return playerRepository.findByEmail(email);
    }

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public PlayerWithStatisticsDTO getPlayerDtoByEmail(String email) {
        Player player = playerRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Player with an email %s doesn't exist!", email))
        );

        return new PlayerWithStatisticsDTO(player);
    }

    public PlayerDTO getPlayerDtoById(Long id) {
        Player player = playerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Player with an id %d doesn't exist!", id))
        );

        return new PlayerDTO(player);
    }

    /**
     * Receives previously validated RegisterInfoDTO object, creates Player object
     * from it and saves it in the database.
     *
     * @param dto
     * @return registered Player object
     */
    public Player registerPlayer(RegisterInfoDTO dto) {
        log.info("Registering a new player: {}", dto.getEmail());
        Player player = createPlayerFromRegisterInfoDTO(dto);
        return playerRepository.save(player);
    }

    private Player createPlayerFromRegisterInfoDTO(RegisterInfoDTO dto) {
        Player player = new Player();
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setEmail(dto.getEmail());
        player.setPassword(passwordEncoder.encode(dto.getPassword()));
        player.setTelephone(dto.getTelephone());

        Role rolePlayer = roleRepository.findByName("ROLE_PLAYER").orElseThrow(
                () -> new InternalServerErrorException("Role ROLE_PLAYER doesn't exist!")
        );

        player.getRoles().add(rolePlayer);

        return player;
    }

    /**
     * Receives a list of players' emails and checks if the players actually exist in the database.
     * @param playersEmails
     * @return true, if the players exist, false if one or more players don't exist in the database.
     */
    public boolean doPlayersExist(List<String> playersEmails) {
        for (String email : playersEmails) {
            Optional<Player> player = getPlayerByEmail(email);
            if (player.isEmpty())
                return false;
        }

        return true;
    }
}
