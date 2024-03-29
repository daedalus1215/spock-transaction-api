package com.example.transactionsapi.controller

import com.example.transactionsapi.exception.AccountNotFoundException
import com.example.transactionsapi.exception.Error
import spock.lang.Specification
import spock.lang.Title

import static utils.RandomGenerator.randomLong

@Title("Test suite for Transactions Api Exception Handler")
class TransactionsApiExceptionHandlerSpec extends Specification {
    def "should return Error"() {
        given: "an accountId"
        final def accountId = randomLong()
        final def target = new TransactionsApiExceptionHandler()

        when: "AccountNotFoundException is thrown"
        final def actual = target.handleAccountNotFoundException(new AccountNotFoundException(accountId))

        then: "returned error will capture the Exception class and message from it"
        actual.getError() == new Error(
                "AccountNotFoundException",
                "The card member account with an id of $accountId was not found.")
    }
}