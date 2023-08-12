package com.bank.transfer.loggingAspest;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.bank.transfer.service.*.*(..))")
    public Object aroundAllServiceMethods(
            ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String method = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
        log.info("Метод {}, в классе {} запущен", method,className);
        try{
            Object result = proceedingJoinPoint.proceed();
            log.info("Метод {}, в классе {} закончил работу {}", method,className, result);
            return result;
        } catch (Throwable e) {
            log.error("В методе {} класса {} произошла ощибка {}: {}",method, className,
                    e.getClass(), e.getMessage());
        }
        return null;
    }
}
