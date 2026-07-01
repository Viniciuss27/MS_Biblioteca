package vinix.resources.exception;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import vinix.services.exception.DatabaseException;
import vinix.services.exception.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	//para registrar no console
	private static final Logger logger = LoggerFactory.getLogger(ResourceExceptionHandler.class);
	
	@ExceptionHandler(ResourceNotFoundException.class) // ResourceNotFoundException
	public ResponseEntity<StandardError> resourceNotFound(
			ResourceNotFoundException e, //excessão com a mensagem de erro
			HttpServletRequest request){ //dados da requisição
		
		logger.error( "Resource Not Found : " + e);
		
		String error = "Resource Not Found" ;
		HttpStatus status = HttpStatus.FOUND; //erro 404
		
		StandardError err = new StandardError(
				Instant.now(),            // momento do erro
				status.value(),           // valor do erro (404)
				error,                    // Nome do erro
				e.getMessage(),           // mensagem personalizada
				request.getRequestURI()); // URL
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)// DatabaseException
	public ResponseEntity<StandardError> dataBase (
			DatabaseException e,
			HttpServletRequest request){
		
		logger.error("Database error : " + e);
		
		String error = "DataBase Integridade violada";
		HttpStatus status = HttpStatus.CONFLICT; // 409
		
		StandardError err = new StandardError(
				Instant.now(), 
				status.value(), 
				error, 
				e.getMessage(), 
				request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<StandardError> exception(
			Exception e,
			HttpServletRequest request){
		
		logger.error("erro não encontrado: " + e);
		
		String error = "Error não encontrado";
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 505
		
		StandardError err = new StandardError(
				Instant.now(),
				status.value(),
				error,
				e.getMessage(),
				request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
}
