package rs.ac.uns.ftn.bachelor_thesis.exception;

import lombok.Getter;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(ResourceType resourceType, String value) {
        super(String.format("%s with %s %s not found!", resourceType.getResourceName(), resourceType.getPropertyName(), value));
    }

    @Getter
    public enum ResourceType {
        APPOINTMENT_ID("Appointment", "id"),
        MANAGER_EMAIL("Manager", "email"),
        MANAGER_ID("Manager", "id"),
        PLAYER_EMAIL("Player", "email"),
        PLAYER_ID("Player", "id"),
        GAME_ID("Game", "id"),
        GROUP_ID("Group", "id");

        private final String resourceName;
        private final String propertyName;

        ResourceType(String resourceName, String propertyName) {
            this.resourceName = resourceName;
            this.propertyName = propertyName;
        }
    }
}
