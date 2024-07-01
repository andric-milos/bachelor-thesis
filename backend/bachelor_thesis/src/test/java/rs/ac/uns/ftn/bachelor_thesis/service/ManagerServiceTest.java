package rs.ac.uns.ftn.bachelor_thesis.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import rs.ac.uns.ftn.bachelor_thesis.exception.ManagerNotFoundException;
import rs.ac.uns.ftn.bachelor_thesis.model.Manager;
import rs.ac.uns.ftn.bachelor_thesis.repository.ManagerRepository;
import rs.ac.uns.ftn.bachelor_thesis.repository.RoleRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagerServiceTest {
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private ManagerService managerService;

    @Nested
    @DisplayName("Method: getManagerByEmail")
    class getManagerByEmailTests {
        @Test
        void shouldReturnManagerForGivenEmail() {
            // Arrange
            String email = "email@email.com";
            Manager expected = new Manager();
            when(managerRepository.findByEmail(email)).thenReturn(Optional.of(expected));

            // Act
            Manager actual = managerService.getManagerByEmail(email);

            // Assert
            assertEquals(expected, actual);
            verify(managerRepository, times(1)).findByEmail(email);
        }

        @Test
        void shouldThrowManagerNotFoundException() {
            // Arrange
            String email = "email@email.com";
            when(managerRepository.findByEmail(email)).thenReturn(Optional.empty());

            // Act
            Executable executable = () -> managerService.getManagerByEmail(email);

            // Assert
            assertThrows(ManagerNotFoundException.class, executable);
            verify(managerRepository, times(1)).findByEmail(email);
        }
    }
}