package com.bank.transfer.modelMapper;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.entity.CardTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperCard {
    private final ModelMapper modelMapper;

    public ModelMapperCard(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CardTransferDto convertToCardTransferDto(CardTransfer cardTransfer) {
        return modelMapper.map(cardTransfer, CardTransferDto.class);
    }

    public CardTransfer convertToCardTransfer(CardTransferDto cardTransferDto) {
        return modelMapper.map(cardTransferDto, CardTransfer.class);
    }
}
