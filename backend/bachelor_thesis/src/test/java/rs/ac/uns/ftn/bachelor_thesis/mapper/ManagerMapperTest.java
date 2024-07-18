package rs.ac.uns.ftn.bachelor_thesis.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import rs.ac.uns.ftn.bachelor_thesis.dto.ManagerDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Manager;
import rs.ac.uns.ftn.bachelor_thesis.model.SportsFacility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ManagerMapperTest {
    private final ManagerMapper managerMapper = new ManagerMapper();

    @Nested
    @DisplayName("Method: fromEntityToDto")
    class fromEntityToDtoTest {
        @Test
        void shouldReturnNullWhenManagerNull() {
            // Arrange
            Manager manager = null;

            // Act
            ManagerDTO dto = managerMapper.fromEntityToDto(manager);

            // Assert
            assertNull(dto);
        }

        @Test
        void shouldReturnExpectedManagerDTOForGivenManager() {
            // Arrange
            SportsFacility sportsFacility = SportsFacility.builder().id(1L).name("Some facility").build();
            Manager manager = Manager.builder()
                    .id(1L).firstName("John").lastName("Doe").email("john@email.com")
                    .password("abcd").telephone("060000000").sportsFacility(sportsFacility)
                    .build();

            // Act
            ManagerDTO actual = managerMapper.fromEntityToDto(manager);

            // Assert
            assertEquals(manager.getId(), actual.getId());
            assertEquals(manager.getFirstName(), actual.getFirstName());
            assertEquals(manager.getLastName(), actual.getLastName());
            assertEquals(manager.getEmail(), actual.getEmail());
            assertEquals(manager.getTelephone(), actual.getTelephone());
            assertEquals(manager.getSportsFacility().getName(), actual.getSportsFacility().getName()); // maybe check the equality of the whole sportsFacility object?
        }

        @Test
        void shouldReturnNullForSportsFacilityInDTOWhenManagersSportsFacilityIsNull() {
            // Arrange
            Manager manager = Manager.builder()
                    .id(1L).firstName("John").lastName("Doe").email("john@email.com")
                    .password("abcd").telephone("060000000")
                    .sportsFacility(null)
                    .build();

            // Act
            ManagerDTO actual = managerMapper.fromEntityToDto(manager);

            // Assert
            assertEquals(manager.getId(), actual.getId());
            assertEquals(manager.getFirstName(), actual.getFirstName());
            assertEquals(manager.getLastName(), actual.getLastName());
            assertEquals(manager.getEmail(), actual.getEmail());
            assertEquals(manager.getTelephone(), actual.getTelephone());
            assertNull(manager.getSportsFacility());
        }
    }
}