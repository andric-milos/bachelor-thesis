package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.bachelor_thesis.dto.GameDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.GoalDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.GoalWithPlayerInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.PlayerDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Game;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;
import rs.ac.uns.ftn.bachelor_thesis.service.GameService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/game")
public class GameController {
    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGameById(@PathVariable("id") Long gameId) {
        Optional<Game> game = gameService.getGameById(gameId);

        if (game.isEmpty())
            return new ResponseEntity<>("Game with an id " + gameId + " does not exist!", HttpStatus.NOT_FOUND);

        List<GoalWithPlayerInfoDTO> goals = new ArrayList<>();
        game.get().getGoals().forEach(goal -> {
            Player player = goal.getPlayer();

            goals.add(new GoalWithPlayerInfoDTO(
                    goal.getId(),
                    game.get().getId(),
                    goal.getTeamColor().toString().toLowerCase(),
                    new PlayerDTO(
                            player.getId(),
                            player.getFirstName(),
                            player.getLastName(),
                            player.getEmail(),
                            player.getTelephone()
                    )
            ));
        });

        return new ResponseEntity<>(new GameDTO(game.get().getId(), goals), HttpStatus.OK);
    }
}
