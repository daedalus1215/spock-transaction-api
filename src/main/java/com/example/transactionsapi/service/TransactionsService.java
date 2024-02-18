package com.example.transactionsapi.service;

import com.example.transactionsapi.exception.AccountNotFoundException;
import com.example.transactionsapi.domain.response.TransactionResponse;
import com.example.transactionsapi.entity.Transaction;
import com.example.transactionsapi.repository.AccountRepository;
import com.example.transactionsapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TransactionsService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionsService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<TransactionResponse> getTransactions(long accountId, String fromDate) throws AccountNotFoundException {
        final List<Transaction> transactionList = fromDate != null
                ? transactionRepository.findAllByAccountAccountIdAndDateGreaterThanEqual(accountId, LocalDate.parse(fromDate).toEpochDay())
                : transactionRepository.findAllByAccount_AccountId(accountId);

        if (transactionList.isEmpty()) {
            accountRepository.findById(accountId)
                    .orElseThrow(() -> new AccountNotFoundException(accountId));
        }

        return transactionList.stream()
                .map(TransactionResponse::of)
                .collect(toList());
    }
}