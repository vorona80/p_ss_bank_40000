package com.bank.transfer.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "card_transfer", schema = "transfer")
public class CardTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "card_number", unique = true)
    private Long cardNumber;

    @Min(50)
    @Column(name = "amount")
    private double amount;

    @Size(min = 3, message = "Причина первода должна содержать минимум 3 знака")
    @Column(name = "purpose")
    private String purpose;

    @Column(name = "account_details_id")
    private Long accountDetailsId;
}
