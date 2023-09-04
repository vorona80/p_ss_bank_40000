package com.bank.transfer.controller;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.exception.NoSuchTransferException;
import com.bank.transfer.modelMapper.ModelMapperAccount;
import com.bank.transfer.service.AccountTransferService;
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
@RequestMapping("/account")
@Tag(name = "Транзакции по номеру АККАУНТА", description = "Методы для работы с транзакциями")
public class RestControllerAccountTransfer {
    private final AccountTransferService accountTransferService;
    private final ModelMapperAccount modelMapperAccount;
    @Autowired
    public RestControllerAccountTransfer(AccountTransferService accountTransferService,
                                         ModelMapperAccount modelMapperAccount) {
        this.accountTransferService = accountTransferService;
        this.modelMapperAccount = modelMapperAccount;
    }

    @GetMapping
    @Operation(summary = "Получение всех транзакций")
    public ResponseEntity<List<AccountTransferDto>> getAllAccountTransfers() {
        List<AccountTransferDto> accountTransfersDto = accountTransferService.getAllAccountTransfers()
                .stream()
                .map(modelMapperAccount::convertToAccountTransferDto)
                .collect(Collectors.toList());
        return !accountTransfersDto.isEmpty()
                ? new ResponseEntity<>(accountTransfersDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Получение тразакции по регистрационному номеру")
    public ResponseEntity<AccountTransferDto>getAccountTransferById(@PathVariable("id") Long id) {
        AccountTransferDto accountTransferDto = modelMapperAccount
                .convertToAccountTransferDto(accountTransferService.getAccountTransfer(id));
        return new ResponseEntity<>(accountTransferDto, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<HttpStatus> addNewAccountTransfer(
            @RequestBody @Valid AccountTransferDto accountTransferDto) {
        accountTransferService.createAccountTransfer(modelMapperAccount
                .convertToAccountTransfer(accountTransferDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> saveUpdateAccountTransfer(
            @RequestBody @Valid AccountTransferDto updateAccountTransferDto,
            @PathVariable("id") Long id) {
        if(updateAccountTransferDto.getId().equals(id)) {
            accountTransferService.update(modelMapperAccount
                    .convertToAccountTransfer(updateAccountTransferDto));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        throw new NoSuchTransferException("Не правильно введен id в запросе на изменение транзакции");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление транзакции по регистрационному номеру из базы данных")
    public String deleteAccountTransfer(@PathVariable("id") Long id) {
        if (accountTransferService.getAccountTransfer(id) == null) {
            return "Транзакции по номеру аккаунта с ID = " + id + " нет в базе данных";
        }
        accountTransferService.delete(id);
        return "Транзакция по номеру аккаунта с ID = " + id + " была удалена";
    }

}
