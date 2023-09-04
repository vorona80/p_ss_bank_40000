package com.bank.transfer.controller;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransfer;
import com.bank.transfer.exception.NoSuchTransferException;
import com.bank.transfer.modelMapper.ModelMapperPhone;
import com.bank.transfer.service.PhoneTransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RestControllerPhoneTransferTest {

    @Mock
    private PhoneTransferService phoneTransferService;
    @Mock
    private ModelMapperPhone modelMapperPhone;
    @InjectMocks
    private RestControllerPhoneTransfer controller;
    @Test
    public void getAllPhoneTransfers_ReturnsValidResponseEntity() {
        PhoneTransferDto phoneTransferDto = new PhoneTransferDto();
        List<PhoneTransfer> phoneAll = List.of(new PhoneTransfer(), new PhoneTransfer());

        Mockito.when(phoneTransferService.getAllPhoneTransfers()).thenReturn(phoneAll);
        Mockito.when(modelMapperPhone.convertToPhoneDto(any())).thenReturn(phoneTransferDto);

        ResponseEntity<List<PhoneTransferDto>> responseEntity = controller.getAllPhoneTransfers();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(2,responseEntity.getBody().size());
        verify(phoneTransferService,times(1)).getAllPhoneTransfers();
    }

    @Test
    public void getAllPhoneTransfers_ReturnsNotValidResponseEntity() {
        Mockito.when(phoneTransferService.getAllPhoneTransfers()).thenReturn(List.of());

        ResponseEntity<List<PhoneTransferDto>> responseEntity = controller.getAllPhoneTransfers();

        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        verify(phoneTransferService,times(1)).getAllPhoneTransfers();

    }

    @Test
    public void getPhoneTransferById() {
        PhoneTransferDto phoneTransferDto = new PhoneTransferDto();
        PhoneTransfer phoneTransfer = new PhoneTransfer();

        Mockito.when(phoneTransferService.getPhoneTransfer(ArgumentMatchers.anyLong()))
                .thenReturn(phoneTransfer);
        Mockito.when(modelMapperPhone.convertToPhoneDto(phoneTransfer))
                .thenReturn(phoneTransferDto);

        ResponseEntity<PhoneTransferDto> responseEntity = controller.getPhoneTransferById(ArgumentMatchers.anyLong());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        verify(phoneTransferService,times(1)).getPhoneTransfer(ArgumentMatchers.anyLong());
    }

    @Test
    public void addNewPhoneTransfer() {
        PhoneTransferDto phoneTransferDto = new PhoneTransferDto();

        ResponseEntity<HttpStatus> responseEntity = controller.addNewPhoneTransfer(phoneTransferDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        verify(phoneTransferService,times(1)).createPhoneTransfer(any());
    }

    @Test
    public void saveUpdatePhoneTransfer_WhenIdMatches() {
        Long id = 1L;
        PhoneTransferDto updatePhoneTransferDto = new PhoneTransferDto();
        updatePhoneTransferDto.setId(id);

        ResponseEntity<HttpStatus> responseEntity = controller
                .saveUpdatePhoneTransfer(updatePhoneTransferDto,id);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        verify(phoneTransferService,times(1)).update(any());
    }

    @Test
    public void saveUpdatePhoneTransfer_WhenIdNotMatch() {
        Long id = 1L;
        PhoneTransferDto updatePhoneTransferDto = new PhoneTransferDto();
        updatePhoneTransferDto.setId(2L);

        Exception exception = assertThrows(NoSuchTransferException.class, () -> {
            controller.saveUpdatePhoneTransfer(updatePhoneTransferDto,id);
        });

        String expectedMessage = "Не правильно введен id в запросе на изменение транзакции";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void deletePhoneTransfer_WhenPhoneTransferExists() {
        Long id = 1L;
        PhoneTransfer phoneTransfer = new PhoneTransfer();

        Mockito.when(phoneTransferService.getPhoneTransfer(id)).thenReturn(phoneTransfer);

        String expectedMessage = "Транзакция по номеру телефона с ID = " + id + " была удалена";
        String actualMessage = controller.deletePhoneTransfer(id);

        assertEquals(expectedMessage,actualMessage);
        verify(phoneTransferService,times(1)).delete(id);
    }

    @Test
    void deletePhoneTransfer_WhenPhoneTransferNotExist() {
        Long id = 1L;

        Mockito.when(phoneTransferService.getPhoneTransfer(id)).thenReturn(null);

        String expectedMessage = "Транзакции по номеру телефона с ID = " + id + " нет в базе данных";
        String actualMessage = controller.deletePhoneTransfer(id);

        assertEquals(expectedMessage,actualMessage);
        verify(phoneTransferService,times(0)).delete(id);
    }
}