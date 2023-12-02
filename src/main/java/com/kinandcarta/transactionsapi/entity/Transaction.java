package com.kinandcarta.transactionsapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
