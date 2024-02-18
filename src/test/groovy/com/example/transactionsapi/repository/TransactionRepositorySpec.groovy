package com.example.transactionsapi.repository

import com.example.transactionsapi.entity.Account
import com.example.transactionsapi.entity.Transaction
import datafixtures.AccountBuilder
import datafixtures.TransactionBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import java.time.LocalDate

@DataJpaTest
@Subject(TransactionRepository)
@Title("Test for Account Repository")
@Narrative("Only covering the actual public contract we expose from the repository")
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

        when: "findAllByAccount_AccountId is invoked"
        final List<Transaction> actual = target.findAllByAccount_AccountId(accountId)

        then:
        "returns only transactions associated with the account id: $accountId"
        actual.size() == 1
        actual.get(0).getAccount().getAccountId() == accountId
    }

    def "should find all transactions associated with an account while being greater than or equal to a date"() {
        given: "an account id and a date"
        final def accountId = FIRST_ACCOUNT_ID

        when: "findAllByAccountAccountIdAndDateGreaterThanEqual is invoked"
        final List<Transaction> actual = target.findAllByAccountAccountIdAndDateGreaterThanEqual(
                accountId,
                LocalDate.of(2022, 2, 2).toEpochDay())

        then:
        "returns only transactions associated with the account id: $accountId, on, or after the given date"
        actual.size() == 2
        actual.collect { transaction -> transaction.transactionId } == [2L, 3L]
    }

    def initializeData() {
        saveFirstAccount()
        saveSecondAccount()
    }

    def saveFirstAccount() {
        final Account account = new AccountBuilder()
                .withAccountId(FIRST_ACCOUNT_ID)
                .build()

        account.setTransactions(Set.of(
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
                .withAccountId(SECOND_ACCOUNT_ID)
                .build()

        final Transaction transaction = new TransactionBuilder()
                .withDate(LocalDate.of(2022, 2, 2).toEpochDay())
                .withAccount(account)
                .build()

        account.setTransactions(Set.of(transaction))

        accountRepository.save(account)
    }
}
