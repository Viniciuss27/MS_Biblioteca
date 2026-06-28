package vinix.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import vinix.services.exceptions.DatabaseException;
import vinix.services.exceptions.ResourceNotFoundException;



@ControllerAdvice
public class ResourceExceptionsHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(
    		ResourceNotFoundException e,
    		HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).
        		body(new StandardError(Instant.
        				now(), status.value(), 
        				"Resource not found", 
        				e.getMessage(), 
        				request.
        				getRequestURI()));
    }
    
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> business(DatabaseException e
    		, HttpServletRequest request) {
        HttpStatus status = HttpStatus.
        		UNPROCESSABLE_ENTITY;
        return ResponseEntity.
        		status(status).
        		body(new StandardError(Instant
        				.now(), 
        				status.value(), "Business rule violation", 
        				e.getMessage(), request.getRequestURI()));
    }
}
