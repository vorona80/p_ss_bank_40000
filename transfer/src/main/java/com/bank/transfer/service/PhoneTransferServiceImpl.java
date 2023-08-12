package com.bank.transfer.service;

import com.bank.transfer.entity.PhoneTransfer;
import com.bank.transfer.exception.NoSuchTransferException;
import com.bank.transfer.repository.PhoneTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class PhoneTransferServiceImpl implements PhoneTransferService{
    private final PhoneTransferRepository phoneTransferRepository;
    @Autowired
    public PhoneTransferServiceImpl(PhoneTransferRepository phoneTransferRepository) {
        this.phoneTransferRepository = phoneTransferRepository;
    }

    @Override
    public List<PhoneTransfer> getAllPhoneTransfers() {
        return phoneTransferRepository.findAll();
    }

    @Override
    public PhoneTransfer createPhoneTransfer(PhoneTransfer phoneTransfer) {
        List<PhoneTransfer> list = phoneTransferRepository.findAll();
        if(list.stream()
                .anyMatch(a -> a.getPhone()
                        .equals(phoneTransfer.getPhone()))) {
            throw new NoSuchTransferException("Транзакция с таким индетификационном номером уже существует");
        }
        return phoneTransferRepository.save(phoneTransfer);
    }
    @Transactional(readOnly = true)
    @Override
    public PhoneTransfer getPhoneTransfer(Long id) {
        try {
            return phoneTransferRepository.findById(id).get();
        } catch (Exception e) {
            throw new NoSuchTransferException("Транзакции с  ID = " + id + " нет в базе");
        }
    }

    @Override
    public void update(PhoneTransfer updatePhoneTransfer) {
        phoneTransferRepository.save(updatePhoneTransfer);
    }

    @Override
    public void delete(Long id) {
        phoneTransferRepository.deleteById(id);
    }
}
