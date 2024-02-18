# spock-transaction-api
* Has Spock tests & Has Pact contract provider test.


Can use `fuzzy-guide` (Api Gateway) repo as the consumer of this API. Can easily run the `fuzzy-guide` consumer tests, to generate the 
`TransactionConsumer-Transactions.json`. Once that file is generated, we can come back to this project and drop it in the:
`test/resources/pacts/` directory. Then we can run the `PactProviderTest` in this repo to prove out that we are honoring the 
contract.