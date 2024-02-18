package com.example.transactionsapi.controller;

import com.example.transactionsapi.domain.response.TransactionResponse;
import com.example.transactionsapi.service.TransactionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountTransactionsController {

    private final TransactionsService transactionsService;

    public AccountTransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionResponse>> getAccountTransactions(@PathVariable long accountId, @RequestParam(required = false) String fromDate) throws AccountNotFoundException {
        return ResponseEntity.ok(transactionsService.getTransactions(accountId, fromDate));
    }
}