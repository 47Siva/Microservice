package com.example.MainServices.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class CustomExceptionHandler extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException ex){
		return ResponseEntity.internalServerError().body(ex.getMessage());
	}
	
}
