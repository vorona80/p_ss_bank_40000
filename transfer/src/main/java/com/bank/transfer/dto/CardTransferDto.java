package com.bank.transfer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Информация о переводке средств по номеру банковской карты")
public class CardTransferDto {
    private Long id;

    @Schema(description = "Номер банковской карты для перевода")
    @Min(value = 1000000, message = "Поле номер карты должно быть минимум 1000000")
    @Max(value = 9999999, message = "Поле карты должно быть максимум 9999999")
    private Long cardNumber;

    @Schema(description = "Сумма к переводу")
    @Min(value = 10, message = "Минимальная сумма перевода 10 долларов")
    private double amount;

    @Schema(description = "Цель перевода")
    @Size(min = 3, message = "Цель перевода должна содержать миниму 3 символов")
    private String purpose;

    private Long accountDetailsId;
}
