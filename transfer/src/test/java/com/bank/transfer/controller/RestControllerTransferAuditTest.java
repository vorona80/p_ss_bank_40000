package com.bank.transfer.controller;

import com.bank.transfer.entity.TransferAudit;
import com.bank.transfer.service.TransferAuditService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class RestControllerTransferAuditTest {

    @InjectMocks
    private RestControllerTransferAudit restControllerTransferAudit;

    @Mock
    private TransferAuditService transferAuditService;

    @Test
    public void testGetAllTransferAuditReturnsAllRecords() {

        List<TransferAudit> auditAll = List.of(new TransferAudit(), new TransferAudit());

        Mockito.when(transferAuditService.getAllAudit()).thenReturn(auditAll);

        List<TransferAudit> result = restControllerTransferAudit.getAllTransferAudit();

        assertEquals(auditAll, result);
        verify(transferAuditService, times(1)).getAllAudit();
    }
}