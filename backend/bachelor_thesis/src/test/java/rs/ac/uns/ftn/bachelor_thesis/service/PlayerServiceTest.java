package rs.ac.uns.ftn.bachelor_thesis.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.uns.ftn.bachelor_thesis.model.Player;
import rs.ac.uns.ftn.bachelor_thesis.repository.PlayerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;
    @InjectMocks
    private PlayerService playerService;

    @Test
    void getPlayerByEmail_shouldReturnExistingPlayer() {
        // Arrange
        String email = "test@gmail.com";
        Player player = new Player();
        player.setEmail(email);

        Mockito.when(playerRepository.findByEmail(email)).thenReturn(Optional.of(player));

        // Act
        Player returnedPlayer = playerService.getPlayerByEmail(email);

        // Assert
        assertEquals(returnedPlayer.getEmail(), email);
    }
}