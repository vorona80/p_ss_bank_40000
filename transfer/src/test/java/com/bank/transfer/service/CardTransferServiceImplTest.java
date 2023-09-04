package com.bank.transfer.service;

import com.bank.transfer.entity.CardTransfer;
import com.bank.transfer.exception.NoSuchTransferException;
import com.bank.transfer.repository.CardTransferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CardTransferServiceImplTest {
    @Mock
    private CardTransferRepository cardTransferRepository;
    @InjectMocks
    private CardTransferServiceImpl cardTransferService;

    @Test
    void getAllCardTransfers() {
        CardTransfer cardTransfer = new CardTransfer();
        CardTransfer cardTransfer1 = new CardTransfer();
        List<CardTransfer> cardAll = List.of(cardTransfer,cardTransfer1);
        Mockito.when(cardTransferRepository.findAll()).thenReturn(cardAll);

        List<CardTransfer> result = cardTransferService.getAllCardTransfers();

        assertNotNull(result);
        assertEquals(cardAll,result);
        assertEquals(2,result.size());

    }

    @Test
    void getCardTransferById() {
        CardTransfer cardTransfer = new CardTransfer();
        Mockito.when(cardTransferRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(cardTransfer));

        CardTransfer result = cardTransferService.getCardTransfer(ArgumentMatchers.anyLong());

        assertNotNull(result);
        assertEquals(cardTransfer,result);
    }

    @Test
    void getAccountTransferById_IfAccountTransferByIdIsNotInDatabase() {
        CardTransfer cardTransfer = new CardTransfer();
        cardTransfer.setId(1L);
        Mockito.when(cardTransferRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchTransferException.class, () -> {
            cardTransferService.getCardTransfer(1L);
        });
    }
    @Test
    void createCardTransfer_IfAccountNumberIsUnique() {
        CardTransfer cardTransfer = new CardTransfer();
        cardTransferService.createCardTransfer(cardTransfer);
        verify(cardTransferRepository,times(1)).save(cardTransfer);
    }
    @Test
    void createCardTransfer_IfAccountNumberIsNotUnique() {
        CardTransfer cardTransfer = new CardTransfer();
        cardTransfer.setCardNumber(1L);

        CardTransfer existingCardTransfer = new CardTransfer();
        existingCardTransfer.setCardNumber(1L);

        Mockito.when(cardTransferRepository.findAll()).thenReturn(List.of(existingCardTransfer));

        Assertions.assertThrows(NoSuchTransferException.class, () -> {
            cardTransferService.createCardTransfer(cardTransfer);
        });
    }

    @Test
    void update() {
        CardTransfer cardTransfer = new CardTransfer();
        cardTransferService.update(cardTransfer);
        verify(cardTransferRepository, times(1)).save(cardTransfer);
    }

    @Test
    void deleteById() {
        CardTransfer cardTransfer = new CardTransfer();
        cardTransfer.setId(1L);
        cardTransferService.delete(1L);
        verify(cardTransferRepository, times(1)).deleteById(1L);
    }
}