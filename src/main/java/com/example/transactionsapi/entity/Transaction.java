package com.example.transactionsapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TRANSACTIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private long transactionId;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private long date;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private double amount;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String merchantName;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String summary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Account account;
}
