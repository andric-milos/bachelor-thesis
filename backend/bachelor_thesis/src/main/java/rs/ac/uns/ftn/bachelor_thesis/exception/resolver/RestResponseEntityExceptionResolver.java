package rs.ac.uns.ftn.bachelor_thesis.exception.resolver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rs.ac.uns.ftn.bachelor_thesis.exception.*;

@ControllerAdvice
public class RestResponseEntityExceptionResolver {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resolveResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ManagerNotFoundException.class)
    public ResponseEntity<?> resolveManagerNotFoundException(ManagerNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputDataException.class)
    public ResponseEntity<?> resolveInvalidDataException(InvalidInputDataException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> resolveUnauthorizedException(UnauthorizedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomizableBadRequestException.class)
    public ResponseEntity<?> resolveCustomizableBadRequestException(CustomizableBadRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<?> resolveInternalServerErrorException(InternalServerErrorException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
