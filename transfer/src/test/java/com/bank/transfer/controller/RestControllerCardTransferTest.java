package com.bank.transfer.controller;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.entity.CardTransfer;
import com.bank.transfer.exception.NoSuchTransferException;
import com.bank.transfer.modelMapper.ModelMapperCard;
import com.bank.transfer.service.CardTransferService;
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
public class RestControllerCardTransferTest {

    @InjectMocks
    private RestControllerCardTransfer restControllerCardTransfer;

    @Mock
    private CardTransferService cardTransferService;

    @Mock
    private ModelMapperCard modelMapperCard;


    @Test
    public void getAllCardTransfers_ReturnsCorrectList() {
        CardTransferDto cardTransferDto = new CardTransferDto();

        List<CardTransfer> cardAll = List.of(new CardTransfer(), new CardTransfer());
        Mockito.when(cardTransferService.getAllCardTransfers()).thenReturn(cardAll);
        Mockito.when(modelMapperCard.convertToCardTransferDto(cardAll.get(0))).thenReturn(cardTransferDto);

        ResponseEntity<List<CardTransferDto>> responseEntity = restControllerCardTransfer.getAllCardTransfers();

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals(cardTransferDto,responseEntity.getBody().get(0));
        verify(cardTransferService, times(1)).getAllCardTransfers();
    }

    @Test
    public void getAllCardTransfers_ReturnsNotFound() {
        Mockito.when(cardTransferService.getAllCardTransfers()).thenReturn(List.of());

        ResponseEntity<List<CardTransferDto>> responseEntity = restControllerCardTransfer.getAllCardTransfers();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(cardTransferService, times(1)).getAllCardTransfers();
    }

    @Test
    public void getCardTransferById() {
        Long id = 1L;
        CardTransfer cardTransfer = new CardTransfer();
        CardTransferDto cardTransferDto = new CardTransferDto();

        when(cardTransferService.getCardTransfer(id)).thenReturn(cardTransfer);
        when(modelMapperCard.convertToCardTransferDto(cardTransfer)).thenReturn(cardTransferDto);

        ResponseEntity<CardTransferDto> responseEntity = restControllerCardTransfer.getCardTransferById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cardTransferDto, responseEntity.getBody());
    }


    @Test
    public void addNewCardTransfer_WhenValidCardTransferDto() {
        CardTransferDto cardTransferDto = new CardTransferDto();
        cardTransferDto.setId(1L);
        cardTransferDto.setCardNumber(1234567L);
        cardTransferDto.setAmount(100);
        cardTransferDto.setPurpose("Test");
        cardTransferDto.setAccountDetailsId(1L);

        ResponseEntity<HttpStatus> responseEntity = restControllerCardTransfer.addNewCardTransfer(cardTransferDto);

        verify(cardTransferService, times(1)).createCardTransfer(any());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity);
    }

    @Test
    public void saveUpdateCardTransfer_WhenIdMatches() {
        Long id = 1L;
        CardTransferDto cardTransferDto = new CardTransferDto();
        cardTransferDto.setId(id);

        ResponseEntity<HttpStatus> responseEntity = restControllerCardTransfer.saveUpdateCardTransfer(cardTransferDto, id);

        verify(cardTransferService, times(1)).update(any());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void saveUpdateCardTransfer_WhenIdNotMatch() {
        Long id = 1L;
        CardTransferDto cardTransferDto = new CardTransferDto();
        cardTransferDto.setId(2L);

        Exception exception = assertThrows(NoSuchTransferException.class, () -> {
            restControllerCardTransfer.saveUpdateCardTransfer(cardTransferDto, id);
        });

        String expectedMessage = "Не правильно введен id в запросе на изменение транзакции";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void deleteCardTransfer_WhenCardTransferExists() {
        Long id = 1L;
        CardTransfer cardTransfer = new CardTransfer();

        Mockito.when(cardTransferService.getCardTransfer(id)).thenReturn(cardTransfer);

        String actualMessage = restControllerCardTransfer.deleteCardTransfer(id);

        assertEquals("Транзакция по номеру карты с ID = " + id + " была удалена", actualMessage);
        verify(cardTransferService, times(1)).delete(id);
    }

    @Test
    public void deleteCardTransfer_WhenCardTransferNotExist() {
        Long id = 1L;

        Mockito.when(cardTransferService.getCardTransfer(id)).thenReturn(null);

        String actualMessage = restControllerCardTransfer.deleteCardTransfer(id);

        assertEquals("Транзакции по номеру карты с ID = " + id + " нет в базе данных", actualMessage);
        verify(cardTransferService, times(0)).delete(id);
    }
}