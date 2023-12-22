package com.cabral.disney.exception;

import com.cabral.disney.payload.response.ValidationErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(GenreNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleGenreNotFoundException(GenreNotFoundException ex) {
        return createNotFoundResponseError(ex.getMessage());
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleMovieNotFoundException(MovieNotFoundException ex) {
        return createNotFoundResponseError(ex.getMessage());
    }

    @ExceptionHandler(MovieSearchEmptyResultException.class)
    public ResponseEntity<Map<String, String>> handleMovieSearchEmptyResultException(MovieSearchEmptyResultException ex) {
        return createNotFoundResponseError(ex.getMessage());
    }

    @ExceptionHandler(CharacterNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCharacterNotFoundException(CharacterNotFoundException ex) {
        return createNotFoundResponseError(ex.getMessage());
    }

    @ExceptionHandler(CharacterSearchEmptyResultException.class)
    public ResponseEntity<Map<String, String>> handleCharacterSearchEmptyResultException(CharacterSearchEmptyResultException ex) {
        return createNotFoundResponseError(ex.getMessage());
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Map<String, String>> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Required request part '" + ex.getRequestPartName() + "' is not present");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<Map<String, String>> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex) {
        return createBadRequestResponseError(ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyTakenException(EmailAlreadyTakenException ex) {
        return createBadRequestResponseError(ex.getMessage());
    }

    @ExceptionHandler(ListCreationValidationException.class)
    public ResponseEntity<Map<String, String>> handleListCreationValidationException(ListCreationValidationException ex) {
        return createBadRequestResponseError(ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return createNotFoundResponseError(ex.getMessage());
    }

    @ExceptionHandler(ListNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleListNotFoundException(ListNotFoundException ex) {
        return createNotFoundResponseError(ex.getMessage());
    }

    @ExceptionHandler(ListNameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleListNameAlreadyExistsException(ListNameAlreadyExistsException ex) {
        return createBadRequestResponseError(ex.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, String>> handleExpiredJwtException() {
        return createForbiddenResponseError("Expired JWT, log in again.");
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Map<String, String>> handleSignatureException(SignatureException ex) {
        return createForbiddenResponseError(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException() {
        return createForbiddenResponseError("Incorrect username or password");
                //podria ser tambien un 401 en vez de 403
    }

    private ResponseEntity<Map<String, String>> createForbiddenResponseError(String exceptionMessage) {
        Map<String, String> response = new HashMap<>();
        response.put("message", exceptionMessage);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    private ResponseEntity<Map<String, String>> createNotFoundResponseError(String exceptionMessage) {
        Map<String, String> response = new HashMap<>();
        response.put("message", exceptionMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    private ResponseEntity<Map<String, String>> createBadRequestResponseError(String exceptionMessage) {
        Map<String, String> response = new HashMap<>();
        response.put("message", exceptionMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
