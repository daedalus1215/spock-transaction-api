package com.kinandcarta.transactionsapi.repository


import datafixtures.AccountBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class AccountRepositorySpec extends Specification {

    @Autowired
    private TransactionRepository transactionRepository

    @Autowired
    private AccountRepository accountRepository

    private final static long FIRST_ACCOUNT_ID = 789L

    def setup() {
        initializeData()
    }

    def "should find account"() {
        given: "an accountId"
        final def accountId = FIRST_ACCOUNT_ID

        when: "findById is invoked with the account id"
        final def actual = accountRepository.findById(accountId)

        then: "returns only the account associated with the id"
        actual.get().getAccountId() == accountId
    }

    def initializeData() {
        saveFirstAccount()
        saveSecondAccount()
    }

    def saveFirstAccount() {
        accountRepository.save(new AccountBuilder()
                .withAccountId(789L)
                .build())
    }

    def saveSecondAccount() {
        accountRepository.save(new AccountBuilder()
                .withAccountId(123L)
                .build())
    }
}
