package com.bank.transfer.service;

import com.bank.transfer.entity.CardTransfer;
import com.bank.transfer.exception.NoSuchTransferException;
import com.bank.transfer.repository.CardTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class CardTransferServiceImpl implements CardTransferService{
    private final CardTransferRepository cardTransferRepository;
    @Autowired
    public CardTransferServiceImpl(CardTransferRepository cardTransferRepository) {
        this.cardTransferRepository = cardTransferRepository;
    }
    @Transactional(readOnly = true)
    @Override
    public List<CardTransfer> getAllCardTransfers() {
        return cardTransferRepository.findAll();
    }

    @Override
    public CardTransfer createCardTransfer(CardTransfer cardTransfer) {
        List<CardTransfer> list = cardTransferRepository.findAll();
        if(list.stream()
                .anyMatch(a -> a.getCardNumber()
                        .equals(cardTransfer.getCardNumber()))){
            throw new NoSuchTransferException("Транзакция с таким индетификационном номером уже существует");
        }
       return cardTransferRepository.save(cardTransfer);
    }
    @   Transactional(readOnly = true)
    @Override
    public CardTransfer getCardTransfer(Long id) {

        try {
            return cardTransferRepository.findById(id).get();
        } catch (Exception e) {
            throw new NoSuchTransferException("Транзакции с  ID = " + id + " нет в базе");
        }
    }

    @Override
    public void update(CardTransfer updateCardTransfer) {

        cardTransferRepository.save(updateCardTransfer);
    }

    @Override
    public void delete(Long id) {
        cardTransferRepository.deleteById(id);
    }
}
