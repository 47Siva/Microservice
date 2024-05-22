//package com.example.Licence.Management.config;
//
//import java.util.UUID;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//	@ExceptionHandler(RuntimeException.class)
//	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex){
//		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
//	}
//	
//	   @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//	    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
//	        if (ex.getRequiredType() == UUID.class) {
//	            return ResponseEntity.badRequest().body("Invalid UUID format provided: " + ex.getValue());
//	        }
//	        return ResponseEntity.badRequest().body("Invalid parameter type");
//	    }
//}
