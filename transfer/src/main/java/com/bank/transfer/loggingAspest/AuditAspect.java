package com.bank.transfer.loggingAspest;

import com.bank.transfer.entity.TransferAudit;
import com.bank.transfer.repository.TransferAuditRepository;
import com.bank.transfer.service.TransferAuditServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;


@Aspect
@Component
public class AuditAspect {
    private final TransferAuditRepository transferAuditRepository;
    private final TransferAuditServiceImpl transferAuditServiceImpl;

    @Autowired
    public AuditAspect(TransferAuditRepository transferAuditRepository,
                       TransferAuditServiceImpl transferAuditServiceImpl
    ) {
        this.transferAuditRepository = transferAuditRepository;
        this.transferAuditServiceImpl = transferAuditServiceImpl;

    }
    @Pointcut("execution(* com.bank.transfer.service.*.update(..))")
    public void methodUpdate() {
    }
    @Pointcut("execution(* com.bank.transfer.service.*.create*(..))")
    public void methodCreate() {
    }

    @Around("methodUpdate() && args(updatedJson)")
    public Object auditUpdated(ProceedingJoinPoint proceedingJoinPoint,
                               Object updatedJson) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String entity = proceedingJoinPoint.getSourceLocation().getWithinType().getName().replace("com.bank.transfer.service.","");

        TransferAudit audit = new TransferAudit();
        List<TransferAudit> auditAll = transferAuditServiceImpl.getAllAudit();
        for (int i=0; i < auditAll.size(); i++) {
            if(auditAll.get(i).getEntityJson().replaceAll(",(.*)", "")
                    .equals(updatedJson.toString().replaceAll(",(.*)", ""))) {
                audit.setCreatedAt(auditAll.get(i).getCreatedAt());
                audit.setCreatedBy(auditAll.get(i).getCreatedBy());
                audit.setEntityJson((auditAll.get(i).getEntityJson()));
                break;
            }
        }
        audit.setEntityType(entity.replace("TransferServiceImpl", ""));
        audit.setOperationType(methodSignature.getName());
        audit.setModifiedBy("admin");
        audit.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        audit.setNewEntityJson(updatedJson.toString());
        transferAuditRepository.save(audit);
        return proceedingJoinPoint.proceed();
    }


    @Around("methodCreate() && args(createJson)")
    public Object methodCreate(ProceedingJoinPoint proceedingJoinPoint,
                               Object createJson) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String entity = proceedingJoinPoint.getSourceLocation().getWithinType().getName().replace("com.bank.transfer.service.","");
        Object result = proceedingJoinPoint.proceed();
        TransferAudit audit = new TransferAudit();

        audit.setEntityJson(result.toString());
        audit.setEntityType(entity.replace("TransferServiceImpl", ""));
        audit.setOperationType(methodSignature.getName().replace("Transfer", ""));
        audit.setCreatedBy("user");
        audit.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        transferAuditRepository.save(audit);
        return result;
    }

}




