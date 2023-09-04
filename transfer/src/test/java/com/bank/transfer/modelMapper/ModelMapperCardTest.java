package com.bank.transfer.modelMapper;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.entity.CardTransfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ModelMapperCardTest {
    @InjectMocks
    public ModelMapperCard modelMapperCard;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        modelMapperCard = new ModelMapperCard(modelMapper);
    }
    @Test
    void testConvertToCardTransfer() {
        CardTransferDto cardTransferDto = new CardTransferDto();
        cardTransferDto.setId(1L);
        cardTransferDto.setCardNumber(111111L);
        cardTransferDto.setAmount(500L);
        cardTransferDto.setPurpose("Phone");
        cardTransferDto.setAccountDetailsId(2L);

        CardTransfer cardTransfer = modelMapperCard.convertToCardTransfer(cardTransferDto);

        assertEquals(cardTransferDto.getId(),cardTransfer.getId());
        assertEquals(cardTransferDto.getCardNumber(),cardTransfer.getCardNumber());
        assertEquals(cardTransferDto.getAmount(),cardTransfer.getAmount());
        assertEquals(cardTransferDto.getPurpose(),cardTransfer.getPurpose());
        assertEquals(cardTransferDto.getAccountDetailsId(),cardTransfer.getAccountDetailsId());
    }

    @Test
    void convertToCardTransferDto() {
        // Arrange
        CardTransfer cardTransfer = new CardTransfer();
        cardTransfer.setId(1L);
        cardTransfer.setCardNumber(1234567L);
        cardTransfer.setAmount(100.0);
        cardTransfer.setPurpose("Test Purpose");
        cardTransfer.setAccountDetailsId(2L);

        CardTransferDto cardTransferDto = modelMapperCard.convertToCardTransferDto(cardTransfer);

        assertEquals(cardTransfer.getId(), cardTransferDto.getId());
        assertEquals(cardTransfer.getCardNumber(), cardTransferDto.getCardNumber());
        assertEquals(cardTransfer.getAmount(), cardTransferDto.getAmount());
        assertEquals(cardTransfer.getPurpose(), cardTransferDto.getPurpose());
        assertEquals(cardTransfer.getAccountDetailsId(), cardTransferDto.getAccountDetailsId());
    }
}