package vinix.resources.exceptions;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import vinix.services.exceptions.DataBaseException;
import vinix.services.exceptions.ResourceNotFoundException;

@ControllerAdvice // exceções de TODOS os controllers
public class ResourceExceptionHandler {

    //para registrar erros no console 
    private static final Logger logger = LoggerFactory.getLogger(ResourceExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class) // do Service
    public ResponseEntity<StandardError> resourceNotFound(
            ResourceNotFoundException e,     // exceção com a mensagem de erro
            HttpServletRequest request) {   // dados da requisição (URL, método)

        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND; // HTTP 404

        StandardError err = new StandardError(
            Instant.now(),           // momento do erro
            status.value(),          // valor do erro (404)
            error,                   // "nome do erro"
            e.getMessage(),          // "mensagem personalizada"
            request.getRequestURI()  // "url ("/users/1")
        );

        return ResponseEntity.status(status).body(err); // retorna 404 + JSON
    }

    @ExceptionHandler(DataBaseException.class) // do Service
    public ResponseEntity<StandardError> database(
            DataBaseException e,
            HttpServletRequest request) {

        logger.error("Database error: ", e); // log do erro

        String error = "Database integrity violation";
        HttpStatus status = HttpStatus.CONFLICT; // HTTP 409 

        StandardError err = new StandardError(
            Instant.now(),           // momento do erro
            status.value(),          // 409
            error,                   // "Database error"
            e.getMessage(),          // mensagem da exceção do banco
            request.getRequestURI()  // qual endpoint causou o erro
        );

        return ResponseEntity.status(status).body(err); // retorna 409 + JSON
    }

    @ExceptionHandler(Exception.class) // exceção para qualquer erro não tratado
    public ResponseEntity<StandardError> generic(
            Exception e,
            HttpServletRequest request) {

        logger.error("Unexpected error: ", e); // log do erro

        String error = "Unexpected error";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // HTTP 500

        StandardError err = new StandardError(
            Instant.now(),           // momento do erro
            status.value(),          // 500
            error,                   // "Unexpected error"
            e.getMessage(),          // mensagem da exceção
            request.getRequestURI()  // endpoint que deu erro
        );

        return ResponseEntity.status(status).body(err); // retorna 500 + JSON
    }
}