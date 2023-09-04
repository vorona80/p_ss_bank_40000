package com.bank.transfer.controller;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.exception.NoSuchTransferException;
import com.bank.transfer.modelMapper.ModelMapperPhone;
import com.bank.transfer.service.PhoneTransferService;
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
@RequestMapping("/phone")
@Tag(name = "Транзакции по номеру ТЕЛЕФОНА", description = "Методы для работы с транзакциями по номеру телефона")
public class RestControllerPhoneTransfer {
    private final PhoneTransferService phoneTransferService;
    private final ModelMapperPhone modelMapperPhone;
    @Autowired
    public RestControllerPhoneTransfer(PhoneTransferService phoneTransferService, ModelMapperPhone modelMapperPhone) {
        this.phoneTransferService = phoneTransferService;
        this.modelMapperPhone = modelMapperPhone;
    }

    @GetMapping
    @Operation(summary = "Получение всех транзакций")
    public ResponseEntity<List<PhoneTransferDto>> getAllPhoneTransfers () {
        List<PhoneTransferDto> phoneTransfersDto = phoneTransferService.getAllPhoneTransfers()
                .stream()
                .map(modelMapperPhone::convertToPhoneDto)
                .collect(Collectors.toList());
        return !phoneTransfersDto.isEmpty()
                ?new ResponseEntity<>(phoneTransfersDto, HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение транзакции по id")
    public ResponseEntity<PhoneTransferDto> getPhoneTransferById (@PathVariable("id") Long id) {
        return new ResponseEntity<>(modelMapperPhone.
                convertToPhoneDto(phoneTransferService.getPhoneTransfer(id)), HttpStatus.OK);

    }

    @PostMapping
    @Operation(summary = "Создание новой транзакции")
    public ResponseEntity<HttpStatus> addNewPhoneTransfer (
            @Valid @RequestBody PhoneTransferDto phoneTransferDto) {
        phoneTransferService.createPhoneTransfer(modelMapperPhone
                .convertToPhone(phoneTransferDto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изминение транзакци по id")
    public ResponseEntity<HttpStatus> saveUpdatePhoneTransfer(
            @Valid @RequestBody PhoneTransferDto updatePhoneTransferDto,
            @PathVariable("id") Long id) {
        if(updatePhoneTransferDto.getId().equals(id)) {
            phoneTransferService.update(modelMapperPhone
                    .convertToPhone(updatePhoneTransferDto));
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        throw new NoSuchTransferException("Не правильно введен id в запросе на изменение транзакции");

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаленрие транзакции")
    public String deletePhoneTransfer(@PathVariable("id") Long id) {
        if(phoneTransferService.getPhoneTransfer(id) == null) {
            return "Транзакции по номеру телефона с ID = " + id + " нет в базе данных";
        }
        phoneTransferService.delete(id);
        return "Транзакция по номеру телефона с ID = " + id + " была удалена";
    }


}
