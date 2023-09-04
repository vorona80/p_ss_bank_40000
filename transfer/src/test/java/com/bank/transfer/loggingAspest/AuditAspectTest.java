package com.bank.transfer.loggingAspest;

import com.bank.transfer.entity.TransferAudit;
import com.bank.transfer.repository.TransferAuditRepository;
import com.bank.transfer.service.TransferAuditServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditAspectTest {
    @Mock
    private TransferAuditRepository transferAuditRepository;
    @Mock
    private TransferAuditServiceImpl transferAuditService;
    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;
    @Mock
    private MethodSignature methodSignature;
    @Mock
    private SourceLocation sourceLocation;
    @InjectMocks
    private AuditAspect auditAspect;

    @Test
    public void auditUpdated() throws Throwable {
        TransferAudit audit = new TransferAudit();
        audit.setEntityJson("id=1,abc");
        List<TransferAudit> auditAll = List.of(audit);
        Mockito.when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        Mockito.when(proceedingJoinPoint.getSourceLocation()).thenReturn(sourceLocation);
        Mockito.when(sourceLocation.getWithinType()).thenReturn(TransferAuditServiceImpl.class);
        Mockito.when(methodSignature.getName()).thenReturn("update");
        Mockito.when(transferAuditService.getAllAudit()).thenReturn(auditAll);
        String updatedJson = "id=1,ptr";

        auditAspect.auditUpdated(proceedingJoinPoint, updatedJson);

        verify(transferAuditRepository, times(1)).save(any(TransferAudit.class));
    }

    @Test
    public void auditCreate() throws Throwable {
        Mockito.when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        Mockito.when(proceedingJoinPoint.getSourceLocation()).thenReturn(sourceLocation);
        Mockito.when(sourceLocation.getWithinType()).thenReturn(TransferAuditServiceImpl.class);
        Mockito.when(methodSignature.getName()).thenReturn("create");
        Mockito.when(proceedingJoinPoint.proceed()).thenReturn("result");
        String createJson = "id=1";

        auditAspect.methodCreate(proceedingJoinPoint, createJson);

        verify(transferAuditRepository, times(1)).save(any(TransferAudit.class));
    }
}