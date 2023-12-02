package com.kinandcarta.transactionsapi.exception;

import lombok.Value;

@Value
public class Error {
    String type;
    String message;
}
