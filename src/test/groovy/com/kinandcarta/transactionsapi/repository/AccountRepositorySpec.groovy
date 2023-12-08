package com.kinandcarta.transactionsapi.repository


import datafixtures.AccountBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@DataJpaTest
@Title("Test for Account Repository")
@Narrative("Only covering the actual public contract we expose from the repository")
class AccountRepositorySpec extends Specification {

    @Autowired
    private AccountRepository target

    private final static long FIRST_ACCOUNT_ID = 789L

    def setup() {
        initializeData()
    }

    def "should find account"() {
        given: "an accountId"
        final def accountId = FIRST_ACCOUNT_ID

        when: "findById is invoked with accountId: $accountId"
        final def actual = target.findById(accountId)

        then: "returns only the account associated with $accountId"
        actual.get().getAccountId() == accountId
    }

    def initializeData() {
        saveFirstAccount()
        saveSecondAccount()
    }

    def saveFirstAccount() {
        target.save(new AccountBuilder()
                .withAccountId(FIRST_ACCOUNT_ID)
                .build())
    }

    def saveSecondAccount() {
        target.save(new AccountBuilder().build())
    }
}
