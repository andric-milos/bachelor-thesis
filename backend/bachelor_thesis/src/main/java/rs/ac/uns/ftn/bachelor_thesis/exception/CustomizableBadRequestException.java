package rs.ac.uns.ftn.bachelor_thesis.exception;

public class CustomizableBadRequestException extends RuntimeException {
    public CustomizableBadRequestException(String message) {
        super(message);
    }
}
