package com.bank.transfer.service;

import com.bank.transfer.entity.TransferAudit;
import com.bank.transfer.repository.TransferAuditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class TransferAuditServiceImplTest {
    @Mock
    private TransferAuditRepository transferAuditRepository;
    @InjectMocks
    private TransferAuditServiceImpl transferAuditService;
    @Test
    void getAllAudit() {
        TransferAudit transferAudit = new TransferAudit();
        TransferAudit transferAudit1 = new TransferAudit();
        List<TransferAudit> auditAll = List.of(transferAudit, transferAudit1);

        Mockito.when(transferAuditRepository.findAll()).thenReturn(auditAll);

        List<TransferAudit> result = transferAuditService.getAllAudit();

        assertNotNull(result);
        assertEquals(auditAll,result);
        assertEquals(2,result.size());
    }
}