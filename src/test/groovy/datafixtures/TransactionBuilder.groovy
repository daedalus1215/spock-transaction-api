package datafixtures

import com.kinandcarta.transactionsapi.entity.Account
import com.kinandcarta.transactionsapi.entity.Transaction

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

    //@TODO: Can add the with methods here, if we want to override the random values.

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