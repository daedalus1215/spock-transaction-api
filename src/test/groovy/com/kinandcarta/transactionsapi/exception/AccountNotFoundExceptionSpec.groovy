package com.kinandcarta.transactionsapi.exception


import spock.lang.Specification

import static utils.RandomGenerator.randomLong

class AccountNotFoundExceptionSpec extends Specification {
    def "should return expected message with accountId"() {
        given: "an accountId"
        final def accountId = randomLong()

        when: "AccountNotFoundException is instantiated with accountId"
        final def target = new AccountNotFoundException(accountId);

        then: "it's message will have the accountId passed in, informing us that the card member was not found."
        target.getMessage() == "The card member account with an id of $accountId was not found.";
    }
}