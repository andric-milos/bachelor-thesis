package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.bachelor_thesis.dto.PlayerDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.PlayerWithStatisticsDTO;
import rs.ac.uns.ftn.bachelor_thesis.service.PlayerService;

import java.util.List;

@RestController
@RequestMapping(value = "/player")
public class PlayerController {
    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        return new ResponseEntity<>(playerService.getAllPlayers(), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PlayerWithStatisticsDTO> getPlayerByEmail(@PathVariable String email) {
        return new ResponseEntity<>(playerService.getPlayerDtoByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long id) {
        return new ResponseEntity<>(playerService.getPlayerDtoById(id), HttpStatus.OK);
    }
}
