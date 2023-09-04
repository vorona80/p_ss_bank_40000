package com.bank.transfer.modelMapper;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ModelMapperPhoneTest {
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    public ModelMapperPhone modelMapperPhone;

    @BeforeEach
    void setup() {
        modelMapper = new ModelMapper();
        modelMapperPhone = new ModelMapperPhone(modelMapper);
    }

    @Test
    void convertToPhoneTransfer() {
        PhoneTransferDto phoneTransferDto = new PhoneTransferDto();
        phoneTransferDto.setId(1L);
        phoneTransferDto.setPhone(111111L);
        phoneTransferDto.setAmount(500L);
        phoneTransferDto.setPurpose("Phone");
        phoneTransferDto.setAccountDetailsId(2L);

        PhoneTransfer phoneTransfer = modelMapperPhone.convertToPhone(phoneTransferDto);

        assertEquals(phoneTransferDto.getId(),phoneTransfer.getId());
        assertEquals(phoneTransferDto.getPhone(),phoneTransfer.getPhone());
        assertEquals(phoneTransferDto.getAmount(),phoneTransfer.getAmount());
        assertEquals(phoneTransferDto.getPurpose(),phoneTransfer.getPurpose());
        assertEquals(phoneTransferDto.getAccountDetailsId(),phoneTransfer.getAccountDetailsId());
    }

    @Test
    void convertToPhoneTransferDto() {
        PhoneTransfer phoneTransfer = new PhoneTransfer();
        phoneTransfer.setId(1L);
        phoneTransfer.setPhone(111111L);
        phoneTransfer.setAmount(500L);
        phoneTransfer.setPurpose("Phone");
        phoneTransfer.setAccountDetailsId(2L);

        PhoneTransferDto phoneTransferDto = modelMapperPhone.convertToPhoneDto(phoneTransfer);

        assertEquals(phoneTransfer.getId(),phoneTransferDto.getId());
        assertEquals(phoneTransfer.getPhone(),phoneTransferDto.getPhone());
        assertEquals(phoneTransfer.getAmount(),phoneTransferDto.getAmount());
        assertEquals(phoneTransfer.getPurpose(),phoneTransferDto.getPurpose());
        assertEquals(phoneTransfer.getAccountDetailsId(),phoneTransferDto.getAccountDetailsId());
    }

}