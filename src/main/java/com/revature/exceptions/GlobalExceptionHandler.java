package com.revature.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No user found.")
	@ExceptionHandler(UserNotFoundException.class)
	public void handleUserNotFoundException(Exception exception) {
		log.error(exception.getMessage(), exception);
	}

	@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unable to authenticate user.")
	@ExceptionHandler(AuthenticationException.class)
	public void handleAuthenticationException(Exception exception) {
		log.error(exception.getMessage(), exception);
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Authorization failure.")
	@ExceptionHandler(AuthorizationException.class)
	public void handleAuthorizationException(Exception exception) {
		log.error(exception.getMessage(), exception);
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unable to create user.")
	@ExceptionHandler(RegistrationException.class)
	public void handleRegistrationException(Exception exception) {
		log.error(exception.getMessage(), exception);
	}
}
