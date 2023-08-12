package com.bank.transfer.controller;

import com.bank.transfer.entity.TransferAudit;
import com.bank.transfer.service.TransferAuditServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")
@Tag(name = "Таблица АУДИТА", description = "Методы для работы с аудитом")
public class RestControllerTransferAudit {
    private final TransferAuditServiceImpl transferAuditServiceImpl;
    @Autowired
    public RestControllerTransferAudit(TransferAuditServiceImpl transferAuditServiceImpl) {
        this.transferAuditServiceImpl = transferAuditServiceImpl;
    }

    @GetMapping("/audit")
    @Operation(summary = "Получение всей таблици аудита")
    public List<TransferAudit> getAllTransferAudit() {
        return transferAuditServiceImpl.getAllAudit();
    }
}
