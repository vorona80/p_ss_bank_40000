package com.bank.transfer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Информация о переводке средств по индефикациоонму номеру аккаунта")
public class AccountTransferDto {
    private Long id;

    @Schema(description = "Индефикационный номер аккаунта для перевода")
    @Min(value = 10000, message = "Поле номер аккаунта должно быть минимум 10000")
    @Max(value = 9999999, message = "Поле аккаунта должно быть максимум 9999999")
    private Long accountNumber;

    @Schema(description = "Сумма к переводу")
    @Min(value = 10, message = "Cумма перевода миниму 10 долларов")
    private double amount;

    @Schema(description = "Цель перевода")
    @Size(min = 3, message = "Цель перевода должна содержать миниму 3 символов")
    private String purpose;

    private Long accountDetailsId;
}
