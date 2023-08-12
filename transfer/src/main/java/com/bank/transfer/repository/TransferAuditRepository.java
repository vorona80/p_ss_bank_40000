package com.bank.transfer.repository;

import com.bank.transfer.entity.TransferAudit;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferAuditRepository extends JpaRepository<TransferAudit, Long> {
}
