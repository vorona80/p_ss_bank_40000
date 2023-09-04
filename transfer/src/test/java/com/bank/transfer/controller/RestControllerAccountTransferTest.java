package com.bank.transfer.controller;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.exception.NoSuchTransferException;
import com.bank.transfer.modelMapper.ModelMapperAccount;
import com.bank.transfer.service.AccountTransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestControllerAccountTransferTest {
    @Mock
    AccountTransferService accountTransferService;
    @Mock
    ModelMapperAccount modelMapperAccount;
    @InjectMocks
    RestControllerAccountTransfer controller;

    @Test
    public void getAllAccountTransfers_ReturnsValidResponseEntity() {
        AccountTransferDto accountTransferDto = new AccountTransferDto();
        List<AccountTransfer> accountAll = List.of(new AccountTransfer(), new AccountTransfer());
        Mockito.when(accountTransferService.getAllAccountTransfers()).thenReturn(accountAll);
        Mockito.when(modelMapperAccount.convertToAccountTransferDto(any())).thenReturn(accountTransferDto);

        ResponseEntity<List<AccountTransferDto>> responseEntity = controller.getAllAccountTransfers();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals(accountTransferDto, responseEntity.getBody().get(0));
        verify(accountTransferService, times(1)).getAllAccountTransfers();
    }
    @Test
    public void getAllAccountTransfers_ReturnsNotValidResponseEntity() {
        Mockito.when(accountTransferService.getAllAccountTransfers()).thenReturn(List.of());

        ResponseEntity<List<AccountTransferDto>> responseEntity = controller.getAllAccountTransfers();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(accountTransferService,times(1)).getAllAccountTransfers();
    }
    @Test
    public void getCardTransferById() {
        Long id = 1L;
        AccountTransferDto accountTransferDto = new AccountTransferDto();
        AccountTransfer accountTransfer = new AccountTransfer();

        Mockito.when(accountTransferService.getAccountTransfer(id)).thenReturn(accountTransfer);
        Mockito.when(modelMapperAccount.convertToAccountTransferDto(accountTransfer)).thenReturn(accountTransferDto);

        ResponseEntity<AccountTransferDto> responseEntity = controller.getAccountTransferById(id);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        verify(accountTransferService,times(1)).getAccountTransfer(id);
    }

    @Test
    public void addNewAccountTransfer() {
        AccountTransferDto accountTransferDto = new AccountTransferDto();

        ResponseEntity<HttpStatus> responseEntity = controller.addNewAccountTransfer(accountTransferDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        verify(accountTransferService, times(1)).createAccountTransfer(any());
    }

    @Test
    public void saveUpdateCardTransfer_WhenIdMatches() {
        Long id = 1L;
        AccountTransferDto updateAccountTransferDto = new AccountTransferDto();
        updateAccountTransferDto.setId(id);

        ResponseEntity<HttpStatus> responseEntity = controller
                .saveUpdateAccountTransfer(updateAccountTransferDto,id);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        verify(accountTransferService,times(1)).update(any());
    }

    @Test
    public void saveUpdateCardTransfer_WhenIdNotMatch() {
        Long id = 1L;
        AccountTransferDto updateAccountTransferDto = new AccountTransferDto();
        updateAccountTransferDto.setId(2L);

        Exception exception = assertThrows(NoSuchTransferException.class, () -> {
            controller.saveUpdateAccountTransfer(updateAccountTransferDto,id);
        });

        String expectedMessage = "Не правильно введен id в запросе на изменение транзакции";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void deleteAccountTransfer_WhenAccountTransferExists() {
        Long id = 1L;
        AccountTransfer accountTransfer = new AccountTransfer();

        Mockito.when(accountTransferService.getAccountTransfer(id)).thenReturn(accountTransfer);

        String expectedMessage = "Транзакция по номеру аккаунта с ID = " + id + " была удалена";
        String actualMessage = controller.deleteAccountTransfer(id);

        assertEquals(expectedMessage,actualMessage);
        verify(accountTransferService,times(1)).delete(id);
    }

    @Test
    void deleteAccountTransfer_WhenAccountTransferNotExist() {
        Long id = 1L;

        Mockito.when(accountTransferService.getAccountTransfer(id)).thenReturn(null);

        String expectedMessage = "Транзакции по номеру аккаунта с ID = " + id + " нет в базе данных";
        String actualMessage = controller.deleteAccountTransfer(id);

        assertEquals(expectedMessage, actualMessage);
        verify(accountTransferService,times(0)).delete(id);
    }
}