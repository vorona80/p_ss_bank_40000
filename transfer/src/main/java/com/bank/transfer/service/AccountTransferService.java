package com.bank.transfer.service;

import com.bank.transfer.entity.AccountTransfer;

import java.util.List;

public interface AccountTransferService {
    public List<AccountTransfer> getAllAccountTransfers();
    public AccountTransfer createAccountTransfer(AccountTransfer accountTransfer);
    public AccountTransfer getAccountTransfer(Long id);
    public void update(AccountTransfer updateAccountTransfer);
    public void delete(Long id);
}
