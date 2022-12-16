package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.bachelor_thesis.dto.GameDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.GoalDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Game;
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

        List<GoalDTO> goals = new ArrayList<>();
        game.get().getGoals().forEach(goal -> {
            goals.add(new GoalDTO(
                    goal.getId(),
                    game.get().getId(),
                    goal.getTeamColor().toString().toLowerCase(),
                    goal.getPlayer().getEmail()
            ));
        });

        return new ResponseEntity<>(new GameDTO(game.get().getId(), goals), HttpStatus.OK);
    }
}
