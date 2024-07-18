package rs.ac.uns.ftn.bachelor_thesis.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import rs.ac.uns.ftn.bachelor_thesis.model.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LocationDTOTest {
    @Nested
    @DisplayName("Constructor: LocationDTO(Location location)")
    class constructorTests {
        @Test
        void shouldInitializeLocationDTOCorrectly() {
            // Arrange
            Location location = Location.builder().id(1L).address("Some random address").longitude(15.0).latitude(15.0).build();

            // Act
            LocationDTO actual = new LocationDTO(location);

            // Assert
            assertEquals(location.getId(), actual.getId());
            assertEquals(location.getAddress(), actual.getAddress());
            assertEquals(location.getLatitude(), actual.getLatitude());
            assertEquals(location.getLongitude(), actual.getLongitude());
        }

        @Test
        void shouldInitializeLocationDTOWithAllFieldsAsNullsWhenPassedLocationIsNull() {
            // Act
            LocationDTO actual = new LocationDTO(null);

            // Assert
            assertNull(actual.getId());
            assertNull(actual.getAddress());
            assertNull(actual.getLatitude());
            assertNull(actual.getLongitude());
        }
    }
}