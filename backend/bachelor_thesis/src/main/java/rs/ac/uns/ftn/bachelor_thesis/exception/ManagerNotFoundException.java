package rs.ac.uns.ftn.bachelor_thesis.exception;

public class ManagerNotFoundException extends RuntimeException {
    public ManagerNotFoundException(String email) {
        super(String.format("Manager with email %s not found!", email));
    }
}
