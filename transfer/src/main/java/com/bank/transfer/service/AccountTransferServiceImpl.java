package com.bank.transfer.service;

import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.exception.NoSuchTransferException;
import com.bank.transfer.repository.AccountTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountTransferServiceImpl implements AccountTransferService{
    private final AccountTransferRepository accountTransferRepository;
    @Autowired
    public AccountTransferServiceImpl(AccountTransferRepository accountTransferRepository) {
        this.accountTransferRepository = accountTransferRepository;
    }
    @Transactional(readOnly = true)
    @Override
    public List<AccountTransfer> getAllAccountTransfers() {
        return accountTransferRepository.findAll();
    }

    @Override
    public AccountTransfer createAccountTransfer(AccountTransfer accountTransfer) {
        List<AccountTransfer> list = accountTransferRepository.findAll();
        if(list.stream()
                .anyMatch(a -> a.getAccountNumber()
                        .equals(accountTransfer.getAccountNumber()))){
            throw new NoSuchTransferException("Транзакция с таким индетификационном номером уже существует");
        }
        return accountTransferRepository.save(accountTransfer);
    }
    @Transactional(readOnly = true)
    @Override
    public AccountTransfer getAccountTransfer(Long id) {
        try {
            return accountTransferRepository.findById(id).get();
        } catch (Exception e) {
            throw new NoSuchTransferException("Транзакции с  ID = " + id + " нет в базе");
        }
    }

    @Override
    public void update(AccountTransfer updateAccountTransfer) {
        accountTransferRepository.save(updateAccountTransfer);
    }

    @Override
    public void delete(Long id) {
        accountTransferRepository.deleteById(id);
    }
}
