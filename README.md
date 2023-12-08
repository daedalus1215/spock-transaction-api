# transactions-api
1. Gradle Sync
2. Run `verification/check` under gradle's tasks

# Article:

The majority of projects that handle requests follow similar request to response flows and have similar patterns.
Generally, if a requirement comes our way for to:
* fetch all transactions associated with an `accountId`, and
* fetch all transactions associated with an `accountId`, on, or after a certain `fromDate`,

We roughly know we need:
* 1 Controller (eg: `AccountTransactionsController`)
    * with a method (eg: `getAccountTransactions()`) that we can register a route to (eg: `/accounts/{accountId}/transactions`). This method will be responsible for receiving a request and returning a response.
* 1 or more Domain Service (eg: `TransactionsService`) to do any orchestration and biz logic.
* 1 or more Repositories (eg: `AccountRepository` and `TransactionRepository`) to fetch data
* 1 or more Entities (eg: `Account` and `Transaction`), which will represent and hold the data we fetch via our Repositories.
* 1 or more Data Transfer Objects (dto) (eg: `TransactionResponse`), which will be the response returned by our request handler (i.e. Controller's method).

------
Journey1 branch
------
One can write tests from either end of their project. In this project, we start with the controller. This requires:
1. The Controller, the system under test (target).
2. The Domain Service, a service we will mock behavior for.
3. The dto, which will be returned by our controller (actual result)

The focus here will be to write a Spec for the Controller (eg:`AccountTransactionsControllerSpec`). Then in the Spec,
mock the Domain Service (eg: `TransactionsService`). The Controller requires the Service to return a list of DTOs
(eg: `TransactionResponse`s), we can mock the returns, and focus on the logic in the Service later.

------
Journey2 branch
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
Journey3 branch
------
The last classes we need to test are our repositories. We will not write unit tests for repositories, since they are interfaces. Also,
their functionality is coupled to the database, so the really ought to be tested with the database in mind. Which means we are going to write integration tests.

The methods we use in the repositories are what we must test for, for our two repositories we have a total of 3 methods we must test.
* 2 in `TransactionRepository`
* 1 in `AccountRepository`
  In order to leverage the Spring Data Jpa, we need to bring in `spock-spring` as a dependency.