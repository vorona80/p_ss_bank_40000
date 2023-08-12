package com.bank.transfer.modelMapper;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperAccount {
    private final ModelMapper modelMapper;
    @Autowired
    public ModelMapperAccount(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AccountTransferDto convertToAccountTransferDto(AccountTransfer accountTransfer) {
        return modelMapper.map(accountTransfer, AccountTransferDto.class);
    }

    public AccountTransfer convertToAccountTransfer(AccountTransferDto accountTransferDto) {
        return modelMapper.map(accountTransferDto, AccountTransfer.class);

    }
}
