package com.example.transactionsapi.service

import com.example.transactionsapi.domain.response.TransactionResponse
import com.example.transactionsapi.exception.AccountNotFoundException
import com.example.transactionsapi.repository.AccountRepository
import com.example.transactionsapi.repository.TransactionRepository
import datafixtures.AccountBuilder
import datafixtures.TransactionBuilder
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

import java.time.LocalDate

@Title("Test for Transaction Service")
class TransactionServiceSpec extends Specification {
    private TransactionRepository transactionRepositoryMock
    private AccountRepository accountRepositoryMock
    private TransactionsService target

    def setup() {
        transactionRepositoryMock = Mock(TransactionRepository)
        accountRepositoryMock = Mock(AccountRepository)
        target = new TransactionsService(transactionRepositoryMock, accountRepositoryMock)
    }

    def "should retrieve transaction with a date equal to or greater than fromDate"() {
        given: "a fromDate and an account Id"
        final def fromDate = "2023-11-19"
        final def startDate = LocalDate.parse(fromDate)
        final def accountId = 21324L
        final def transaction = new TransactionBuilder().build()

        when: "getTransactions is invoked"
        final def actual = target.getTransactions(accountId, fromDate)

        then: "returns expected transaction"
        verifyAll {
            1 * transactionRepositoryMock
                    .findAllByAccountAccountIdAndDateGreaterThanEqual(accountId, startDate.toEpochDay()) >> [transaction]
            0 * accountRepositoryMock.findById(accountId)
            0 * transactionRepositoryMock.findAllByAccount_AccountId(_ as String)
        }
        actual == [TransactionResponse.of(transaction)]
    }

    def "should retrieve transaction"() {
        given: "an accountId"
        final def accountId = 21324L
        final def transaction = new TransactionBuilder().build()

        when: "getTransactions is invoked"
        final def actual = target.getTransactions(accountId, null)

        then: "returns expected transaction"
        verifyAll {
            1 * transactionRepositoryMock
                    .findAllByAccount_AccountId(accountId) >> [transaction]
            0 * accountRepositoryMock.findById(accountId)
            0 * transactionRepositoryMock.findAllByAccountAccountIdAndDateGreaterThanEqual(_ as String, _ as long)
        }
        actual == [TransactionResponse.of(transaction)]
    }

    def "should retrieve empty transactions"() {
        given: "an accountId"
        final def accountId = 21324L
        final def transaction = new TransactionBuilder().build()

        when: "getTransaction is invoked but repository returns no results"
        final def actual = target.getTransactions(accountId, null)

        then: "transactions will be empty"
        verifyAll {
            1 * transactionRepositoryMock.findAllByAccount_AccountId(accountId) >> List.of()
            1 * accountRepositoryMock.findById(accountId) >> Optional.of(new AccountBuilder()
                    .withAccountId(accountId)
                    .withTransactions([transaction])
                    .build())
            0 * transactionRepositoryMock.findAllByAccountAccountIdAndDateGreaterThanEqual(_ as String, _ as long)
        }
        actual.isEmpty()
    }

    def "should throw an AccountNotFoundException"() {
        given: "an accountId"
        final def accountId = 21324L

        transactionRepositoryMock.findAllByAccount_AccountId(accountId) >> List.of()
        accountRepositoryMock.findById(accountId) >> Optional.empty()

        when: "getTransactions is invoked but no Account is found"
        target.getTransactions(accountId, null)

        then: "throws an AccountNotFoundException"
        final AccountNotFoundException actual = thrown(AccountNotFoundException)
        actual.message == "The card member account with an id of ${accountId} was not found."
    }
}