package rs.ac.uns.ftn.bachelor_thesis.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.dto.GameDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.GoalWithPlayerInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.PlayerDTO;
import rs.ac.uns.ftn.bachelor_thesis.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.bachelor_thesis.model.Game;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;
import rs.ac.uns.ftn.bachelor_thesis.repository.GameRepository;

import java.util.ArrayList;
import java.util.List;

import static rs.ac.uns.ftn.bachelor_thesis.exception.ResourceNotFoundException.ResourceType.GAME_ID;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameDTO getGameById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(GAME_ID, String.valueOf(id)));

        List<GoalWithPlayerInfoDTO> goals = new ArrayList<>();
        game.getGoals().forEach(goal -> {
            Player player = goal.getPlayer();

            goals.add(new GoalWithPlayerInfoDTO(
                    goal.getId(),
                    game.getId(),
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

        return new GameDTO(game.getId(), goals);
    }
}
