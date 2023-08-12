package com.bank.transfer.controller;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.exception.NoSuchTransferException;
import com.bank.transfer.modelMapper.ModelMapperCard;
import com.bank.transfer.service.CardTransferServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/card")
@Tag(name = "Транзакции по номеру БАНКОВСКОЙ КАРТЫ", description = "Методы для работы с транзакциями")
public class RestControllerCardTransfer {
    private final CardTransferServiceImpl cardTransferServiceImpl;
    private final ModelMapperCard modelMapperCard;
    @Autowired
    public RestControllerCardTransfer(CardTransferServiceImpl cardTransferServiceImpl, ModelMapperCard modelMapperCard) {
        this.cardTransferServiceImpl = cardTransferServiceImpl;
        this.modelMapperCard = modelMapperCard;
    }

    @GetMapping("")
    @Operation(summary = "Получение всех транзакций")
    public ResponseEntity<List<CardTransferDto>> getAllCardTransfers () {
        List<CardTransferDto> cardTransfersDto = cardTransferServiceImpl.getAllCardTransfers()
                .stream()
                .map(modelMapperCard::convertToCardTransferDto)
                .collect(Collectors.toList());
        return !cardTransfersDto.isEmpty()
                ? new ResponseEntity<>(cardTransfersDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение транзакции по id")
    public ResponseEntity<CardTransferDto> getCardTransferById (@PathVariable ("id") Long id) {
        return new ResponseEntity<>(modelMapperCard.
                convertToCardTransferDto(cardTransferServiceImpl.getCardTransfer(id)), HttpStatus.OK);
    }

    @PostMapping("")
    @Operation(summary = "Создание новой транзакции")
    public ResponseEntity<HttpStatus> addNewCardTransfer (
            @Valid @RequestBody CardTransferDto cardTransferDto) {
        cardTransferServiceImpl.createCardTransfer(modelMapperCard
                .convertToCardTransfer(cardTransferDto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изминение транзакци по id")
    public ResponseEntity<HttpStatus> saveUpdateCardTransfer(
            @Valid @RequestBody CardTransferDto updateCardTransferDto,
            @PathVariable("id") Long id) {
        if (updateCardTransferDto.getId().equals(id)) {
            cardTransferServiceImpl.update(modelMapperCard
                    .convertToCardTransfer(updateCardTransferDto));
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        throw new NoSuchTransferException("Не правильно введен id в запросе на изменение транзакции");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаленрие транзакции")
    public String deleteCardTransfer(@PathVariable("id") Long id) {
        if(cardTransferServiceImpl.getCardTransfer(id) == null) {
            return "Транзакции по номеру карты с ID = " + id + " нет в базе данных";
        }
        cardTransferServiceImpl.delete(id);
        return "Транзакция по номеру карты с ID = " + id + " была удалена";
    }
}
