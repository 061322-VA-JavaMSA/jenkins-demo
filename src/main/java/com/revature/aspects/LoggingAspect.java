package com.revature.aspects;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	private static Logger log = LoggerFactory.getLogger(LoggingAspect.class);

	@Before("within(com.revature..*)")
	public void logInfo(JoinPoint jp) {
		// retrieving args if any
		Object[] args = jp.getArgs();
		
		// if args are present, log them
		if (args != null) {
			log.info(jp.getSignature().toShortString() + ", args :" + Arrays.toString(args));
		} else {
			log.info(jp.getSignature().toShortString());
		}
	}
}
