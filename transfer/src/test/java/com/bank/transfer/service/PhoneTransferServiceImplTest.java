package com.bank.transfer.service;

import com.bank.transfer.entity.PhoneTransfer;
import com.bank.transfer.exception.NoSuchTransferException;
import com.bank.transfer.repository.PhoneTransferRepository;
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
class PhoneTransferServiceImplTest {
    @Mock
    private PhoneTransferRepository phoneTransferRepository;
    @InjectMocks
    private PhoneTransferServiceImpl phoneTransferService;
    @Test
    void getAllPhoneTransfers() {
        PhoneTransfer phoneTransfer = new PhoneTransfer();
        PhoneTransfer phoneTransfer1 = new PhoneTransfer();
        List<PhoneTransfer> phoneAll = List.of(phoneTransfer,phoneTransfer1);

        Mockito.when(phoneTransferRepository.findAll()).thenReturn(phoneAll);

        List<PhoneTransfer> result = phoneTransferService.getAllPhoneTransfers();

        assertNotNull(result);
        assertEquals(phoneAll,result);
        assertEquals(2,result.size());
    }

    @Test
    void getPhoneTransferById() {
        PhoneTransfer phoneTransfer = new PhoneTransfer();
        Mockito.when(phoneTransferRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(phoneTransfer));

        PhoneTransfer result = phoneTransferService.getPhoneTransfer(ArgumentMatchers.anyLong());

        assertNotNull(result);
        assertEquals(phoneTransfer,result);
    }
    @Test
    void getPhoneTransferById_IfAccountTransferByIdIsNotInDatabase() {
        PhoneTransfer phoneTransfer = new PhoneTransfer();
        phoneTransfer.setId(1L);

        Mockito.when(phoneTransferRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchTransferException.class, () -> {
            phoneTransferService.getPhoneTransfer(1L);
        });
    }

    @Test
    void createPhoneTransfer_IfAccountNumberIsUnique() {
        PhoneTransfer phoneTransfer = new PhoneTransfer();

        phoneTransferService.createPhoneTransfer(phoneTransfer);
        verify(phoneTransferRepository,times(1)).save(phoneTransfer);

    }

    @Test
    void createPhoneTransfer_IfAccountNumberIsNotUnique() {
        PhoneTransfer phoneTransfer = new PhoneTransfer();
        phoneTransfer.setPhone(123456789L);

        PhoneTransfer existingPhoneTransfer = new PhoneTransfer();
        existingPhoneTransfer.setPhone(123456789L);

        Mockito.when(phoneTransferRepository.findAll())
                .thenReturn(List.of(existingPhoneTransfer));

        Assertions.assertThrows(NoSuchTransferException.class, () -> {
            phoneTransferService.createPhoneTransfer(phoneTransfer);
        });

    }
    @Test
    void update() {
        PhoneTransfer phoneTransfer = new PhoneTransfer();

        phoneTransferService.update(phoneTransfer);
        verify(phoneTransferRepository,times(1)).save(phoneTransfer);
    }

    @Test
    void deleteById() {
        PhoneTransfer phoneTransfer = new PhoneTransfer();
        phoneTransfer.setId(1L);

        phoneTransferService.delete(1L);
        verify(phoneTransferRepository,times(1)).deleteById(1L);
    }
}