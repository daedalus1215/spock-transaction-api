package com.kinandcarta.transactionsapi.repository

import com.kinandcarta.transactionsapi.entity.Account
import com.kinandcarta.transactionsapi.entity.Transaction
import datafixtures.AccountBuilder
import datafixtures.TransactionBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

import java.time.LocalDate

import static java.util.Set.of

@DataJpaTest
class TransactionRepositorySpec extends Specification {

    @Autowired
    private TransactionRepository target

    @Autowired
    private AccountRepository accountRepository

    private final static long FIRST_ACCOUNT_ID = 789L
    private final static long SECOND_ACCOUNT_ID = 123L

    def setup() {
        initializeData()
    }

    def "should find all transactions associated with an account"() {
        given: "an accountId"
        final def accountId = SECOND_ACCOUNT_ID

        when: "findAllByAccount_AccountId is invoked with account id"
        final List<Transaction> actual = target.findAllByAccount_AccountId(accountId)

        then: "returns only transactions associated with the account id"
        actual.size() == 1
        actual.get(0).getAccount().getAccountId() == accountId
    }

    def "should find all transactions associated with an account and greater than or equal to a date"() {
        given: "an account id and a date"
        final def accountId = FIRST_ACCOUNT_ID

        when: "findAllByAccountAccountIdAndDateGreaterThanEqual is invoked with given account id and date"
        final List<Transaction> actual = target
                .findAllByAccountAccountIdAndDateGreaterThanEqual(
                        accountId,
                        LocalDate.of(2022, 2, 2).toEpochDay())

        then: "returns only transactions on or after the given date and associated with the account id"
        actual.size() == 2
        actual.collect { transaction -> transaction.getTransactionId() } == [2L, 3L]
    }

    def initializeData() {
        saveFirstAccount()
        saveSecondAccount()
    }

    def saveFirstAccount() {
        final Account account = new AccountBuilder()
                .withAccountId(789L)
                .build()

        account.setTransactions(of(
                new TransactionBuilder()
                        .withAccount(account)
                        .withDate(LocalDate.of(2022, 2, 3).toEpochDay())
                        .withTransactionId(3L)
                        .build(),
                new TransactionBuilder()
                        .withAccount(account)
                        .withDate(LocalDate.of(2022, 2, 2).toEpochDay())
                        .withTransactionId(2L)
                        .build(),
                new TransactionBuilder()
                        .withAccount(account)
                        .withDate(LocalDate.of(2022, 2, 1).toEpochDay())
                        .withTransactionId(1L)
                        .build()
        ))

        accountRepository.save(account)
    }

    def saveSecondAccount() {
        final Account account = new AccountBuilder()
                .withAccountId(123L)
                .build()

        final Transaction transaction = new TransactionBuilder()
                .withDate(LocalDate.of(2022, 2, 2).toEpochDay())
                .withAccount(account)
                .build()

        account.setTransactions(of(transaction))

        accountRepository.save(account)
    }
}
