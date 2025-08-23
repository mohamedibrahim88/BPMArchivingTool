package com.archiving.archivingTool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.naming.NamingException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.CommunicationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Handle general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        log.error("Error occurred while processing request", ex);
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle runtime exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        log.error("Error occurred while processing request", ex);

        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle authentication exceptions
    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<String> handleAuthenticationException(Exception ex) {
        log.error("Error occurred while processing request", ex);

        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }

    // Handle LDAP naming exceptions
    @ExceptionHandler(NamingException.class)
    public ResponseEntity<String> handleNamingException(NamingException ex) {
        log.error("Error occurred while processing request", ex);

        return new ResponseEntity<>("LDAP Operation Failed", HttpStatus.BAD_REQUEST);
    }

    // Handle LDAP communication exceptions
    @ExceptionHandler(CommunicationException.class)
    public ResponseEntity<String> handleCommunicationException(CommunicationException ex) {
        log.error("Error occurred while processing request", ex);

        return new ResponseEntity<>("Cannot connect to LDAP server", HttpStatus.SERVICE_UNAVAILABLE);
    }

    // Handle LDAP authentication not supported
    @ExceptionHandler(AuthenticationNotSupportedException.class)
    public ResponseEntity<String> handleAuthenticationNotSupportedException(
            AuthenticationNotSupportedException ex) {
        log.error("Error occurred while processing request", ex);

        return new ResponseEntity<>("Authentication Not Supported", HttpStatus.BAD_REQUEST);
    }

    // Handle validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(
            MethodArgumentNotValidException ex) {
        log.error("Error occurred while processing request", ex);

        return new ResponseEntity<>("One or more fields are invalid", HttpStatus.BAD_REQUEST);
    }

    // Handle illegal argument exceptions
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        log.error("Error occurred while processing request", ex);


        return new ResponseEntity<>("Invalid Request", HttpStatus.BAD_REQUEST);
    }

}