package com.kinandcarta.transactionsapi.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(long accountId) {
        super("The card member account with an id of " + accountId + " was not found.");
    }
}
