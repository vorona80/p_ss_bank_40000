package com.bank.transfer.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "account_transfer", schema = "transfer",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "account_number")
})
public class AccountTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", unique = true)
    private Long accountNumber;

    private double amount;
    private String purpose;

    @Column(name = "account_details_id")
    private Long accountDetailsId;
}
