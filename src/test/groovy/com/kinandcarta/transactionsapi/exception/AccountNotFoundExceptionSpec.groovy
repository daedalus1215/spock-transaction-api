package com.kinandcarta.transactionsapi.exception


import spock.lang.Specification

import static utils.RandomGenerator.randomLong

class AccountNotFoundExceptionSpec extends Specification {
    def "should return expected message with accountId argument being passed in"() {
        given: "an accountId"
        final def accountId = randomLong()

        when:
        "AccountNotFoundException is instantiated with accountId: ${accountId}"
        final def target = new AccountNotFoundException(accountId);

        then:
        "it's message will have ${accountId} passed in, informing us card member was not found."
        target.getMessage() == "The card member account with an id of ${accountId} was not found.";
    }
}