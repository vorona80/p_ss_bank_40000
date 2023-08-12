package com.bank.transfer.service;

import com.bank.transfer.entity.CardTransfer;

import java.util.List;


public interface CardTransferService {
    public List<CardTransfer> getAllCardTransfers ();
    public CardTransfer createCardTransfer (CardTransfer cardTransfer);
    public CardTransfer getCardTransfer (Long id);
    public void update (CardTransfer updateCardTransfer);
    public void delete (Long id);
}
