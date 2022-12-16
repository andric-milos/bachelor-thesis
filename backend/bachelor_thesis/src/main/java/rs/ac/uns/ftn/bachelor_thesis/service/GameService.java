package rs.ac.uns.ftn.bachelor_thesis.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bachelor_thesis.model.Game;
import rs.ac.uns.ftn.bachelor_thesis.repository.GameRepository;

import java.util.Optional;

@Service
public class GameService {
    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }
}
