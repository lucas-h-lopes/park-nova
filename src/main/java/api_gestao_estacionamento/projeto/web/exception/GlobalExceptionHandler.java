package api_gestao_estacionamento.projeto.web.exception;

import api_gestao_estacionamento.projeto.service.exception.EntityNotFoundException;
import api_gestao_estacionamento.projeto.service.exception.PasswordInvalidPassword;
import api_gestao_estacionamento.projeto.service.exception.UsernameUniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameUniqueViolationException.class)
    public ResponseEntity<CustomExceptionBody> uniqueViolationException(RuntimeException e, HttpServletRequest request){
        log.info("Unique violation exception error: {}", e);
        CustomExceptionBody error = new CustomExceptionBody(request, HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(409).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomExceptionBody> entityNotFoundViolationException(EntityNotFoundException e, HttpServletRequest request){
        log.info("Entity not found error: {}", e);
        CustomExceptionBody error = new CustomExceptionBody(request, HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionBody> validationErrors(HttpServletRequest request, BindingResult result){
        CustomExceptionBody error = new CustomExceptionBody(request, HttpStatus.UNPROCESSABLE_ENTITY, "Dado(s) inválido(s)", result);
        return ResponseEntity.status(422).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomExceptionBody> accessDeniedException(HttpServletRequest request){
        CustomExceptionBody error = new CustomExceptionBody(request, HttpStatus.FORBIDDEN, "O usuário autenticado não possui permissão para acessar este recurso");
        return ResponseEntity.status(403).body(error);
    }

    @ExceptionHandler(PasswordInvalidPassword.class)
    public ResponseEntity<CustomExceptionBody> passwordInvalidException(PasswordInvalidPassword e, HttpServletRequest request){
        CustomExceptionBody error = new CustomExceptionBody(request, HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(400).body(error);
    }
}
