package com.bank.transfer.modelMapper;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperPhone {
    private final ModelMapper modelMapper;

    public ModelMapperPhone(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public PhoneTransferDto convertToPhoneDto(PhoneTransfer phoneTransfer) {
        return modelMapper.map(phoneTransfer, PhoneTransferDto.class);
    }

    public PhoneTransfer convertToPhone(PhoneTransferDto phoneTransferDto) {
        return modelMapper.map(phoneTransferDto, PhoneTransfer.class);
    }

}
