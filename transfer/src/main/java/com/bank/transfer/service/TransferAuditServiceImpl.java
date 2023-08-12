package com.bank.transfer.service;

import com.bank.transfer.entity.TransferAudit;
import com.bank.transfer.repository.TransferAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransferAuditServiceImpl implements TransferAuditService {
    private final TransferAuditRepository transferAuditRepository;
    @Autowired
    public TransferAuditServiceImpl(TransferAuditRepository transferAuditRepository) {
        this.transferAuditRepository = transferAuditRepository;
    }
    @Transactional(readOnly = true)
    @Override
    public List<TransferAudit> getAllAudit() {
        return transferAuditRepository.findAll();
    }
}
