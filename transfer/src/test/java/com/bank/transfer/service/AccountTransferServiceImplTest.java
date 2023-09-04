package com.bank.transfer.service;

import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.exception.NoSuchTransferException;
import com.bank.transfer.repository.AccountTransferRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountTransferServiceImplTest {
    @Mock
    private AccountTransferRepository accountTransferRepository;
    @InjectMocks
    private AccountTransferServiceImpl accountTransferService;

    @Test
    void getAllAccountTransfer() {
        AccountTransfer accountTransfer = new AccountTransfer();
        AccountTransfer accountTransfer1 = new AccountTransfer();
        List<AccountTransfer> accountAll = List.of(accountTransfer1, accountTransfer);

        Mockito.when(accountTransferRepository.findAll()).thenReturn(accountAll);

        List<AccountTransfer> result = accountTransferService.getAllAccountTransfers();

        assertNotNull(result);
        assertEquals(accountAll, result);
        assertEquals(2, result.size());
    }

    @Test
    void getAccountTransferById() {
        AccountTransfer account = new AccountTransfer();
        Mockito.when(accountTransferRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(account));

        AccountTransfer result = accountTransferService.getAccountTransfer(ArgumentMatchers.anyLong());

        assertNotNull(result);
        assertEquals(account, result);
    }

    @Test
    void getAccountTransferById_IfAccountTransferByIdIsNotInDatabase() {
        AccountTransfer account = new AccountTransfer();
        account.setId(1L);

        Mockito.when(accountTransferRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchTransferException.class, () -> {
            accountTransferService.getAccountTransfer(1L);
        });
    }
    @Test
    void createAccountTransfer_IfAccountNumberIsUnique() {
        final AccountTransfer accountTransfer = mock(AccountTransfer.class);

        accountTransferService.createAccountTransfer(accountTransfer);

        verify(accountTransferRepository,times(1)).save(accountTransfer);
    }

    @Test
    void createAccountTransfer_IfAccountNumberIsNotUnique() {
        AccountTransfer accountTransfer = new AccountTransfer();
        accountTransfer.setAccountNumber(12345L);

        AccountTransfer existingAccountTransfer = new AccountTransfer();
        existingAccountTransfer.setAccountNumber(12345L);

        when(accountTransferRepository.findAll()).thenReturn(List.of(existingAccountTransfer));

        Assertions.assertThrows(NoSuchTransferException.class, () -> {
            accountTransferService.createAccountTransfer(accountTransfer);
        });
    }

    @Test
    void update() {
        AccountTransfer accountTransfer = new AccountTransfer();

        accountTransferService.update(accountTransfer);

        verify(accountTransferRepository,times(1)).save(accountTransfer);
    }

    @Test
    void delete() {
        AccountTransfer accountTransfer = new AccountTransfer();
        accountTransfer.setId(1L);

        doNothing().when(accountTransferRepository).deleteById(1L);

        accountTransferService.delete(1L);

        verify(accountTransferRepository,times(1)).deleteById(1L);
    }
}

