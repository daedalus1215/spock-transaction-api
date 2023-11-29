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
The majority of projects that handle requests, now-a-days, follow similar request to response flows and have similar structure and architecture. 
So, when a requirement is requested of us to build a new endpoint that can do 2 things: 
  * fetch all transactions and, 
  * fetch all transactions after a certain date, 
we know the class patterns we will use are generally the same. we know we need: 
  * a Controller (`AccountTransactionsController`) 
    * with a method (`getAccountTransactions()`) that we can register a route to (eg: `/accounts/{accountId}/transactions`)
  * 1 or more Domain Service (to do any orchestration and biz logic we need)
  * 1 or more Repositories to fetch data 
