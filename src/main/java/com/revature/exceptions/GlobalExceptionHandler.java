package com.revature.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No user found.")
	@ExceptionHandler(UserNotFoundException.class)
	public void handleUserNotFoundException() {
		// behavior to be done, ie: log
	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Bad credentials.")
	@ExceptionHandler(AuthenticationException.class)
	public void handleAuthenticationException() {
		// behavior to be done, ie: log
	}
}
