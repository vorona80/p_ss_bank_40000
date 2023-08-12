package com.bank.transfer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Информация о переводке средств по номеру телефона")
public class PhoneTransferDto {
    private Long id;
    @Schema(description = "Номер тедефона для перевода")
    @Min(value = 100000000, message = "Поле номер тедефона должно быть минимум 100000000")
    @Max(value = 999999999, message = "Поле телефона должно быть максимум 999999999")
    private Long phone;
    @Schema(description = "Сумма к переводу")
    @Min(value = 10, message = "Минимальная сумма перевода 10 долларов")
    private double amount;
    @Schema(description = "Цель перевода")
    @Size(min = 3, message = "Цель перевода должна содержать миниму 3 символов")
    private String purpose;
    private Long accountDetailsId;
}
