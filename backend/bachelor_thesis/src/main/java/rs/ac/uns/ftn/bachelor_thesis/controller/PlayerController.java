package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.bachelor_thesis.dto.PlayerDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.PlayerWithStatisticsDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;
import rs.ac.uns.ftn.bachelor_thesis.service.PlayerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/player")
public class PlayerController {
    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAll() {
        List<Player> players = playerService.getAllPlayers();
        List<PlayerDTO> dto = new ArrayList<>();

        players.forEach(player -> { dto.add(new PlayerDTO(player)); });

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getPlayerByEmail(@PathVariable String email) {
        Optional<Player> playerOptional = playerService.getPlayerByEmail(email);

        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();

            // return new ResponseEntity<>(new PlayerDTO(player), HttpStatus.OK);
            return new ResponseEntity<>(new PlayerWithStatisticsDTO(player), HttpStatus.OK);
        }

        return new ResponseEntity<>("Player with email " + email + " doesn't exists!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getPlayerById(@PathVariable Long id) {
        Optional<Player> playerOptional = playerService.getPlayerById(id);

        if (playerOptional.isPresent()) {
            return new ResponseEntity<>(new PlayerDTO(playerOptional.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>("Player with id " + id + " doesn't exists!", HttpStatus.BAD_REQUEST);
    }
}
