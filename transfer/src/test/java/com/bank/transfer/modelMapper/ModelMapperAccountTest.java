package com.bank.transfer.modelMapper;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
class ModelMapperAccountTest {
    @InjectMocks
    private ModelMapperAccount modelMapperAccount;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        modelMapperAccount = new ModelMapperAccount(modelMapper);
    }


    @Test
    void convertToAccountTransfer() {
        AccountTransferDto accountTransferDto = new AccountTransferDto();
        accountTransferDto.setId(1L);
        accountTransferDto.setAccountNumber(111111L);
        accountTransferDto.setAmount(500L);
        accountTransferDto.setPurpose("Phone");
        accountTransferDto.setAccountDetailsId(2L);

        AccountTransfer accountTransfer = modelMapperAccount.convertToAccountTransfer(accountTransferDto);

        assertEquals(accountTransferDto.getId(),accountTransfer.getId());
        assertEquals(accountTransferDto.getAccountNumber(),accountTransfer.getAccountNumber());
        assertEquals(accountTransferDto.getAmount(),accountTransfer.getAmount());
        assertEquals(accountTransferDto.getPurpose(),accountTransfer.getPurpose());
        assertEquals(accountTransferDto.getAccountDetailsId(),accountTransfer.getAccountDetailsId());
    }

    @Test
    void convertToAccountTransferDto() {
        AccountTransfer accountTransfer = new AccountTransfer();
        accountTransfer.setId(1L);
        accountTransfer.setAccountNumber(1234567L);
        accountTransfer.setAmount(100.0);
        accountTransfer.setPurpose("Test Purpose");
        accountTransfer.setAccountDetailsId(2L);

        AccountTransferDto accountTransferDto = modelMapperAccount.convertToAccountTransferDto(accountTransfer);

        assertEquals(accountTransfer.getId(), accountTransferDto.getId());
        assertEquals(accountTransfer.getAccountNumber(), accountTransferDto.getAccountNumber());
        assertEquals(accountTransfer.getAmount(), accountTransferDto.getAmount());
        assertEquals(accountTransfer.getPurpose(), accountTransferDto.getPurpose());
        assertEquals(accountTransfer.getAccountDetailsId(), accountTransferDto.getAccountDetailsId());
    }
}