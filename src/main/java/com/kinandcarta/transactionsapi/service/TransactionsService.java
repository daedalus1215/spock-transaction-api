package com.kinandcarta.transactionsapi.service;

import com.kinandcarta.transactionsapi.domain.response.TransactionResponse;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TransactionsService {

    public List<TransactionResponse> getTransactions(long accountId, String fromDate) {
        return null;
    }
}