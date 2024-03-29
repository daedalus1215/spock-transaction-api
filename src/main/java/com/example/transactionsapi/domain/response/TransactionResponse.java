package com.example.transactionsapi.domain.response;

import com.example.transactionsapi.entity.Transaction;
import com.example.transactionsapi.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private String transactionId;
    private String date;
    private double amount;
    private String merchantName;
    private String summary;
    private String accountId;

    public TransactionResponse() {
    }

    public static TransactionResponse of(Transaction transaction) {
        return new TransactionResponse(
                String.valueOf(transaction.getTransactionId()),
                DateUtils.formatEpochDateAsString(transaction.getDate(), "yyyy-MM-dd"),
                transaction.getAmount(),
                transaction.getMerchantName(),
                transaction.getSummary(),
                String.valueOf(transaction.getAccount().getAccountId())
        );
    }
}