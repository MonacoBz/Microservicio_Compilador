//package com.app.lexema.exceptions;
//
//import jakarta.validation.ConstraintViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.time.LocalDateTime;
//
//@ControllerAdvice
//public class ValidationExceptions{
//@ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ErrorMessage> ConstraintViolationException(ConstraintViolationException ex){
//    String message = ex.getConstraintViolations().iterator().next().getMessage().substring(0,30);
//    ErrorMessage error = new ErrorMessage(message, LocalDateTime.now());
//    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//}
//@ExceptionHandler(TokenNoValidEx.class)
//    public ResponseEntity<ErrorMessage> TokenNoValidEx(TokenNoValidEx ex){
//    String message = ex.getMessage();
//    ErrorMessage error = new ErrorMessage(message,LocalDateTime.now());
//    return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
//}
//}
