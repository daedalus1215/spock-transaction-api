package datafixtures

import com.example.transactionsapi.entity.Account
import com.example.transactionsapi.entity.Transaction

import java.time.LocalDate

import static utils.RandomGenerator.randomLong
import static utils.RandomGenerator.randomDouble
import static utils.RandomGenerator.randomString

class TransactionBuilder {
    private long transactionId = randomLong()
    private long date = LocalDate.parse("2023-11-19").toEpochDay()
    private double amount = randomDouble()
    private String merchantName = randomString()
    private String summary = randomString()
    private Account account = new AccountBuilder().build()

    TransactionBuilder withAccount(Account account) {
        this.account = account
        return this
    }

    TransactionBuilder withDate(long date) {
        this.date = date
        return this
    }

    TransactionBuilder withTransactionId(long transactionId) {
        this.transactionId = transactionId
        return this
    }

    def build() {
        new Transaction(
                transactionId: transactionId,
                date: date,
                amount: amount,
                merchantName: merchantName,
                summary: summary,
                account: account,
        )
    }
}