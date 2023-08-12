package com.bank.transfer.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "phone_transfer", schema = "transfer")
public class PhoneTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "phone_number")
//    @Pattern(regexp = "/^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$/")
    private Long phone;

    @Column(name = "amount")
    private double amount;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "account_details_id")
    private Long accountDetailsId;

}
