package ru.t1.gladun.starter.aspect;

import ch.qos.logback.classic.Level;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.t1.gladun.starter.exception.WorkingProcessException;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
public class MainAspect {


    Logger logger = LoggerFactory.getLogger(MainAspect.class.getName());
    String logLevel;

    public MainAspect(String logLevel) {
        this.logLevel = logLevel;
        ch.qos.logback.classic.Logger rootLogger =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.toLevel(logLevel));
    }

    @Around("@annotation(ru.t1.gladun.starter.service.LogTracking)")
    public Object logTracking(ProceedingJoinPoint proceedingJoinPoint) {
        logger.info("Start calling method {}", proceedingJoinPoint.getSignature().toShortString());
        logger.debug(String.format("The method has %d args", Arrays.stream(proceedingJoinPoint.getArgs()).count()));
        logger.debug(String.format("The method has signature %s: ", proceedingJoinPoint.getSignature().toLongString()));
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            logger.error(String.format("Big Bab Error: %s", e.getMessage()), e);
            throw new WorkingProcessException("Something wrong");
        }

        logger.info("Args of method {}: {}", proceedingJoinPoint.getSignature().toShortString(), Arrays.stream(proceedingJoinPoint.getArgs()).map(Object::toString)
                .collect(Collectors.joining(", ")));
        logger.info("Method {} returned: {}", proceedingJoinPoint.getSignature().toShortString(), result);
        logger.info("Finish calling method {}", proceedingJoinPoint.getSignature().toShortString());
        return result;
    }
}
