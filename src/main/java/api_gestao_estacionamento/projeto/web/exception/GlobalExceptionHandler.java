package api_gestao_estacionamento.projeto.web.exception;

import api_gestao_estacionamento.projeto.service.exception.*;
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

    @ExceptionHandler({UsernameUniqueViolationException.class, ActivationTokenAlreadyUsedException.class, CpfUniqueViolationException.class})
    public ResponseEntity<CustomExceptionBody> uniqueViolationExceptionHandler(RuntimeException e, HttpServletRequest request) {
        logError(request, e);
        CustomExceptionBody error = new CustomExceptionBody(request, HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(409).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomExceptionBody> entityNotFoundViolationExceptionHandler(EntityNotFoundException e, HttpServletRequest request) {
        logError(request, e);
        CustomExceptionBody error = new CustomExceptionBody(request, HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionBody> validationExceptionHandler(HttpServletRequest request, BindingResult result) {
        CustomExceptionBody error = new CustomExceptionBody(request, HttpStatus.UNPROCESSABLE_ENTITY, "Dado(s) inválido(s)", result);
        return ResponseEntity.status(422).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomExceptionBody> accessDeniedExceptionHandler(HttpServletRequest request) {
        CustomExceptionBody error = new CustomExceptionBody(request, HttpStatus.FORBIDDEN, "O usuário autenticado não possui permissão para acessar este recurso");
        return ResponseEntity.status(403).body(error);
    }

    @ExceptionHandler(InactiveAccountException.class)
    public ResponseEntity<CustomExceptionBody> customForbiddenExceptionHandler(InactiveAccountException e, HttpServletRequest request) {
        logError(request, e);
        CustomExceptionBody error = new CustomExceptionBody(request, HttpStatus.FORBIDDEN, e.getMessage());
        return ResponseEntity.status(403).body(error);
    }

    @ExceptionHandler({PasswordInvalidPassword.class, InvalidActivationTokenException.class, InvalidTemplateException.class})
    public ResponseEntity<CustomExceptionBody> badRequestExceptionHandler(RuntimeException e, HttpServletRequest request) {
        logError(request, e);
        CustomExceptionBody error = new CustomExceptionBody(request, HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomExceptionBody> genericExceptionHandler(Exception e, HttpServletRequest request) {
        logError(request, e);
        CustomExceptionBody error = new CustomExceptionBody(request, HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado");
        return ResponseEntity.status(500).body(error);
    }

    private void logError(HttpServletRequest request, Exception e) {
        log.info("Error at {}: {}", request.getRequestURI(), e.getMessage());
    }
}
