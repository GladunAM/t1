package ru.t1.gladun.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MainAspect {

    Logger logger = LoggerFactory.getLogger(MainAspect.class.getName());

    @Before("@annotation(ru.t1.gladun.service.LogExecution)")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Before calling method {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing("@annotation(ru.t1.gladun.service.LogException)")
    public void logAfterThrowing(JoinPoint joinPoint) {
        logger.info("Exception throwing in method {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "@annotation(ru.t1.gladun.service.GettingResult)",
            returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, String result) {
        logger.info("Method was calling {}", joinPoint.getSignature().toShortString());
        logger.info("Result is \"{}\"", result);

    }

    @Around("@annotation(ru.t1.gladun.service.LogTracking)")
    public Object logTracking(ProceedingJoinPoint proceedingJoinPoint) {
        logger.info("Around start calling method {}", proceedingJoinPoint.getSignature().toShortString());
        long startTime = System.currentTimeMillis();
        Object result = null;
        try{
            result = proceedingJoinPoint.proceed();
        }catch (Throwable e){
            throw new RuntimeException();
        }

        long endTime = System.currentTimeMillis();
        logger.info("Around Finish calling method {}", proceedingJoinPoint.getSignature().toShortString());
        logger.info("Method worked in {} ms", endTime - startTime);
        return result;
    }
}
