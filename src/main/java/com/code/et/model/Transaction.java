package com.code.et.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @NotNull
    @Column(name = "amount")
    private Long amount;
    @Column(name = "note")
    private String note;
    @Column(name = "transaction_date")
    private Long transactionDate;

    public Transaction(Long amount, String note, Long transactionDate) {
        this.amount = amount;
        this.note = note;
        this.transactionDate = transactionDate;
    }
}