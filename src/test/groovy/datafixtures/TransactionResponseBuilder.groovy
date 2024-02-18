package datafixtures


import com.example.transactionsapi.domain.response.TransactionResponse

import static utils.RandomGenerator.randomLong
import static utils.RandomGenerator.randomBigDecimal
import static utils.RandomGenerator.randomString

class TransactionResponseBuilder {
    private long transactionId = randomLong()
    private long date = randomLong()
    private double amount = randomBigDecimal()
    private String merchantName = randomString()
    private String summary = randomString()

    //@TODO: Can add the with methods here, if we want to override the random values.

    def build() {
        new TransactionResponse(
                transactionId: transactionId,
                date: date,
                amount: amount,
                merchantName: merchantName,
                summary: summary
        )
    }
}