package com.bank.transfer.loggingAspest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoggingAspectTest {

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    @Mock
    private Signature signature;

    @InjectMocks
    private LoggingAspect loggingAspect;

    @Test
    void aroundAllServiceMethodsLogsCorrectMessages() throws Throwable {
        Mockito.when(proceedingJoinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn("methodName");
        Mockito.when(proceedingJoinPoint.getTarget()).thenReturn(new Object());
        Mockito.when(proceedingJoinPoint.proceed()).thenReturn("result");

        loggingAspect.aroundAllServiceMethods(proceedingJoinPoint);
    }

    @Test
    void aroundAllServiceMethodsLogsCorrectErrorMessage() throws Throwable {
        when(proceedingJoinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("methodName");
        when(proceedingJoinPoint.getTarget()).thenReturn(new Object());
        Throwable throwable = new RuntimeException("error message");
        when(proceedingJoinPoint.proceed()).thenThrow(throwable);

        loggingAspect.aroundAllServiceMethods(proceedingJoinPoint);
       }

}