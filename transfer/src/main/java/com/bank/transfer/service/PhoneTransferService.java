package com.bank.transfer.service;

import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.entity.PhoneTransfer;
import liquibase.pro.packaged.P;

import java.util.List;

public interface PhoneTransferService {
    public List<PhoneTransfer> getAllPhoneTransfers();
    public PhoneTransfer createPhoneTransfer(PhoneTransfer phoneTransfer);
    public PhoneTransfer getPhoneTransfer(Long id);
    public void update(PhoneTransfer updatePhoneTransfer);
    public void delete(Long id);

}
