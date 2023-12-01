package datafixtures


import com.kinandcarta.transactionsapi.domain.response.TransactionResponse

import static datafixtures.RandomGenerator.*

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