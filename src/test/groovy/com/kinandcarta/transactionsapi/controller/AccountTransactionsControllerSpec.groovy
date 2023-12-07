package com.kinandcarta.transactionsapi.controller

import com.kinandcarta.transactionsapi.exception.AccountNotFoundException
import com.kinandcarta.transactionsapi.service.TransactionsService
import datafixtures.TransactionResponseBuilder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static utils.RandomGenerator.randomInt
import static utils.RandomGenerator.randomString

class AccountTransactionsControllerSpec extends Specification {
    private TransactionsService transactionsServiceMock
    private MockMvc mvcMock
    private AccountTransactionsController target

    def setup() {
        transactionsServiceMock = Mock(TransactionsService)
        target = new AccountTransactionsController(transactionsServiceMock)
        mvcMock = MockMvcBuilders.standaloneSetup(target)
                .setControllerAdvice(new TransactionsApiExceptionHandler())
                .build()
    }

    def "should retrieve empty list of transactions when none found by TransactionService"() {
        given: "an accountId"
        final def accountId = 123

        when: "request is made against registered request handler with accountId"
        mvcMock.perform(get("/accounts/{accountId}/transactions", accountId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isEmpty())

        then: "get transactions will be invoked with accountId and will return empty list of transactions"
        1 * transactionsServiceMock.getTransactions(accountId, null) >> []
    }

    def "should return not found error with message of error in body"() throws Exception {
        given: "an accountId"
        final def accountId = 123

        when: "request is made against registered request handler with accountId"
        mvcMock.perform(get("/accounts/{accountId}/transactions", accountId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath('$.error.type').value("AccountNotFoundException"))
                .andExpect(jsonPath('$.error.message').value('The card member account with an id of 123 was not found.'))

        then: "get transactions will be invoked with accountId and throw AccountNotFoundException"
        1 * transactionsServiceMock.getTransactions(accountId, null) >> { throw new AccountNotFoundException(accountId) }
    }

    def "should retrieve transactions without a fromDate in request parameter"() {
        given: "an accountId as a path variable"
        final def transaction = new TransactionResponseBuilder().build()
        final def accountId = randomInt()

        when: "request is made against registered request handler with the accountId"
        mvcMock.perform(get("/accounts/{accountId}/transactions", accountId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$[0].transactionId').value(transaction.transactionId))
                .andExpect(jsonPath('$[0].date').value(transaction.date))
                .andExpect(jsonPath('$[0].amount').value(transaction.amount))
                .andExpect(jsonPath('$[0].merchantName').value(transaction.merchantName))
                .andExpect(jsonPath('$[0].summary').value(transaction.summary))

        then: "get transactions will be invoked with accountId and will return expected transactions"
        1 * transactionsServiceMock.getTransactions(accountId, null) >> [transaction]
    }

    def "should retrieve transactions with a fromDate in request parameter"() {
        given: "an account Id as a path variable and fromDate as a request parameter"
        final def transaction = new TransactionResponseBuilder().build()
        final def accountId = randomInt()
        final def fromDate = randomString()

        when: "request is made against registered request handler with the accountId and fromDate"
        mvcMock.perform(get("/accounts/{accountId}/transactions", accountId)
                .param("fromDate", fromDate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$[0].transactionId').value(transaction.transactionId))
                .andExpect(jsonPath('$[0].date').value(transaction.date))
                .andExpect(jsonPath('$[0].amount').value(transaction.amount))
                .andExpect(jsonPath('$[0].merchantName').value(transaction.merchantName))
                .andExpect(jsonPath('$[0].summary').value(transaction.summary))

        then: "get transactions will be invoked with accountId and fromDate and will return expected transactions"
        1 * transactionsServiceMock.getTransactions(accountId, fromDate) >> [transaction]
    }
}