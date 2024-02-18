package com.example.transactionsapi.controller


import datafixtures.TransactionResponseBuilder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static utils.RandomGenerator.randomInt
import static utils.RandomGenerator.randomString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AccountTransactionsControllerSpec extends Specification {
    private com.example.transactionsapi.service.TransactionsService transactionsServiceMock
    private MockMvc mvcMock
    private AccountTransactionsController target

    def setup() {
        transactionsServiceMock = Mock(com.example.transactionsapi.service.TransactionsService)
        target = new AccountTransactionsController(transactionsServiceMock)
        mvcMock = MockMvcBuilders.standaloneSetup(target)
                .build()
    }

    def "should retrieve transactions without a fromDate in request parameter"() {
        given: "an accountId as a path variable"
        def transaction = new TransactionResponseBuilder().build()
        def accountId = randomInt()

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

    def "should retrieve with a fromDate in request parameter"() {
        given: "an account Id as a path variable and fromDate as a request parameter"
        def transaction = new TransactionResponseBuilder().build()
        def accountId = randomInt()
        def fromDate = randomString()

        when: "request is made against registered request handler with the accountId and fromDate"
        1 * transactionsServiceMock.getTransactions(accountId, fromDate) >> [transaction]

        then: "get transactions will be invoked with accountId and fromDate and will return expected transactions"
        mvcMock.perform(get("/accounts/{accountId}/transactions", accountId)
                .param("fromDate", fromDate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$[0].transactionId').value(transaction.transactionId))
                .andExpect(jsonPath('$[0].date').value(transaction.date))
                .andExpect(jsonPath('$[0].amount').value(transaction.amount))
                .andExpect(jsonPath('$[0].merchantName').value(transaction.merchantName))
                .andExpect(jsonPath('$[0].summary').value(transaction.summary))
    }
}