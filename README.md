# transactions-api

**AS** a developer on the Cardmember Behavior Analytics team<br/>
**I WANT** to retrieve card member transactions<br/>
**SO THAT** I can analyze and model card member behavior to better present the card member with relevant promotional
material

_Scenario 1: Retrieve transactions for account with no transactions_

**GIVEN** a card member account with no transactions<br/>
**WHEN** I request a list of transactions for that account<br/>
**THEN** I will receive a success response<br/>
**AND** I will see an empty list of transactions

_Scenario 2: Account does not exist in database_

**GIVEN** a card member account id that does not exist<br/>
**WHEN** I request a list of transactions for that account<br/>
**THEN** I will receive a not found response<br/>
**AND** I will see a detailed not found error message

_Scenario 3: Retrieve transactions for an account with one or more transactions_

**GIVEN** a card member account with transactions<br/>
**WHEN** I request a list of transactions for that account<br/>
**THEN** I will receive a success response<br/>
**AND** I will a list of all their transactions

_Scenario 4: Retrieve transactions for an account after a given a date_

**GIVEN** a card member account with transactions<br/>
**WHEN** I request a list of transactions after the given date<br/>
**THEN** I will receive a success response<br/>
**AND** I will see a list of transactions that occurred on and after the given date

## Notes:

GET `/accounts/{accountId}/transactions?fromDate=2021-01-31`

### Sample Response:

```json
[
  {
    "transactionId": "1",
    "date": "2022-02-03",
    "amount": 50.00,
    "merchantName": "Amazon",
    "summary": "XP Explained (Book)",
    "accountId": "123"
  }
]
```


# Article:
The majority of projects that handle requests, now-a-days, follow similar request to response flows and have similar structure / architecture. 
So, when a requirement is requested of us to build a new endpoint, in a new service project, that can do 2 things: 
  * fetch all transactions associated with an `accountId`, and 
  * fetch all transactions associated with an `accountId`, after or on a certain `fromDate`, 

we know the class patterns we will use are generally the same:
  * 1 Controller (eg: `AccountTransactionsController`) 
    * with a method (eg: `getAccountTransactions()`) that we can register a route to (eg: `/accounts/{accountId}/transactions`). This method will be responsible for receiving a request and returning a response.
  * 1 or more Domain Service/App Services (eg: `TransactionsService`) to do any orchestration and biz logic.
  * 1 or more Repositories (eg: `AccountRepository` and `TransactionRepository`) to fetch data
  * 1 or more Entities (eg: `Account` and `Transaction`), which will represent and hold the data we fetch via our Repositories.
  * 1 or more Data Transfer Objects (dto) (eg: `TransactionResponse`), which will be the response returned by our request handler (i.e. Controller's method).

------
START OF JOURNEY1
------
We can begin the development from either end of our project, or really anywhere in between. I've chosen to start with the controller, for no particular reason. This requires:
1. The Controller, the SUT (our target for the test)
2. The App service, the mock
3. The dto, the (actual) result we expect to see

Our focus here will be to write a Spec for the Controller, and in the Spec we will mock the App Service. We do not need the App Service to actually return anything currently, we just need its contract for mocking purposes. 

------
START OF JOURNEY2
------
The domain we are working in has entities (`Transaction`) that reference other entities (`Account`), in a many-to-one relationship. As we move over to our Service, we will need to 
build these entities up and have our service focus on 3 things:
1. Handle the difference of whether the `fromDate` is provided. If not, we want all transactions associated with the `accountId`; otherwise, we want all transactions associated with the `accountId` after or on the provided `fromDate`.
2. If we get back no transactions from the query, we will verify that the system at least has an account with the `accountId` passed in. If not, throw an exception; otherwise, continue.
3. Convert our `Transaction` entities into `TransactionResponse`.

In order to do this we need 3 classes:
1. `Transaction` (entity)
2. `Account` (entity)
3. `TransactionRepository`
4. `AccountRepository`

There is additional work, where we will need to modify our Controller. Since the Service can throw an Exception.
The Controller will leverage a `@RestControllerAdvice` on a new Api exception handling class. So we will need to update our Controller's Spec. 

------
START OF JOURNEY3
------
The last classes we need to test are our repositories. We will not write unit tests for repositories, since they are interfaces. Also,
their functionality is coupled to the database, so the really ought to be tested with the database in mind. Which means we are going to write integration tests.

The methods we use in the repositories are what we must test for, for our two repositories we have a total of 3 methods we must test. 
* 2 in `TransactionRepository`
* 1 in `AccountRepository`
In order to leverage the Spring Data Jpa, we need to bring in `spock-spring` as a dependency. 