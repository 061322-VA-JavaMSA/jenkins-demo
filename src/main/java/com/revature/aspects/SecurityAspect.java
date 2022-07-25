package com.revature.aspects;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.annotations.Secured;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.AuthorizationException;
import com.revature.models.User;
import com.revature.services.UserService;


@Component
@Aspect
public class SecurityAspect {

	private UserService us;
	private HttpServletRequest request;
	
	@Autowired
	public SecurityAspect(UserService us, HttpServletRequest request) {
		super();
		this.us = us;
		this.request = request;
	}


	@Around("@annotation(com.revature.annotations.Secured)")
	public Object secureEndpoint(ProceedingJoinPoint pjp) throws Throwable {
		
		/*
		 * Retrieving the method with @Secured
		 * Retrieving the annotation itself
		 */
		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		Secured securedAnnotation = method.getAnnotation(Secured.class);
		
		/*
		 * Retrieving allowed roles for that controller method
		 */
		List<String> allowedRoles = Arrays.asList(securedAnnotation.allowedRoles());
		
		// retrieving Auth header from request, throw exception if none is provided
		String token = request.getHeader("Authorization");
		if(token == null) {
			throw new AuthenticationException();
		}
		
		// token "[id]:[role]"
		// {"[id]", "[role]"}
		String[] tokens = token.split(":");
		
		//retrieving user in based on id in token
		User principal = us.getUserById(Integer.valueOf(tokens[0]));
		
		/*-
		 * complex validation
		 * 
		 * make sure that the role from db match the role in header
		 */
		if(principal == null || !principal.getRole().toString().equals(tokens[1])|| !allowedRoles.contains(principal.getRole().toString())) {
			throw new AuthorizationException();
		}
		
		return pjp.proceed();
	}
	
}
