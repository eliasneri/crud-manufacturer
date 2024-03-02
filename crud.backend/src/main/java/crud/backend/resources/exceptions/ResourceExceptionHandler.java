package crud.backend.resources.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import crud.backend.services.exceptions.ManufactureDatabaseIntegrity;
import crud.backend.services.exceptions.ManufactureInvalidCNPJ;
import crud.backend.services.exceptions.ManufactureNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ManufactureNotFoundException.class)
    public ResponseEntity<StandardError> ManufactureNotFound (ManufactureNotFoundException e, HttpServletRequest request) {
        HttpStatus status =  HttpStatus.NOT_FOUND;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Empresa não encontrada");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ManufactureInvalidCNPJ.class)
    public ResponseEntity<StandardError> ManufactureInvalidCNPJ (ManufactureInvalidCNPJ e, HttpServletRequest request) {
        HttpStatus status =  HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(String.valueOf(HttpStatus.BAD_REQUEST));
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ManufactureDatabaseIntegrity.class)
    public ResponseEntity<StandardError> ManufactureDatabaseViolation (ManufactureDatabaseIntegrity e, HttpServletRequest request){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(String.valueOf(e.getCause()));
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);

    }
}
