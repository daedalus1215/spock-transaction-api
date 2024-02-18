package datafixtures


import com.example.transactionsapi.entity.Account
import com.example.transactionsapi.entity.Transaction

import static utils.RandomGenerator.randomLong
import static utils.RandomGenerator.randomString

class AccountBuilder {
    private long accountId = randomLong()
    private String memberName = randomString()
    private List<Transaction> transactions

    //@TODO: Can add the with methods here, if we want to override the random values.

    AccountBuilder withAccountId(long accountId) {
        this.accountId = accountId
        return this
    }

    AccountBuilder withMemberName(String memberName) {
        this.memberName = memberName
        return this
    }

    AccountBuilder withTransactions(List<Transaction> transactions) {
        this.transactions = transactions
        return this
    }

    def build() {
        new Account(
                accountId: accountId,
                memberName: memberName,
                transactions: transactions
        )
    }
}