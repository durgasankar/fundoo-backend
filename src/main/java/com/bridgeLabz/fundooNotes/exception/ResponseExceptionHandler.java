package com.bridgeLabz.fundooNotes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgeLabz.fundooNotes.response.Response;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> handleAllException(UserException userException) {
		return new ResponseEntity<>(new Response(userException.getMessage(), 400), HttpStatus.BAD_GATEWAY);
	}
}
