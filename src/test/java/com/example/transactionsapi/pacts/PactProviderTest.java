package com.example.transactionsapi.pacts;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.example.transactionsapi.entity.Account;
import com.example.transactionsapi.entity.Transaction;
import com.example.transactionsapi.repository.AccountRepository;
import com.example.transactionsapi.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("Transactions")
@PactFolder("pacts")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PactProviderTest {
    private static final long ACCOUNT_ID = 555;
    @LocalServerPort
    public int port;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @BeforeAll
    public void setUpTestData() {
        Account account = new Account(ACCOUNT_ID, "memberName", Collections.emptySet());
        Account savedAccount = accountRepository.save(account);

        final Transaction xp =
                new Transaction(
                        1,
                        LocalDate.of(2022, 2, 1).toEpochDay(),
                        12.00,
                        "Amazon",
                        "XP Explained (Book)",
                        account);

        final Transaction desk =
                new Transaction(
                        2, LocalDate.of(2022, 2, 2).toEpochDay(), 350.0, "Walmart", "Standing Desk", account);

        transactionRepository.save(xp);
        transactionRepository.save(desk);
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void PactProviderTest(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    public void setup(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @State(value = "transactions exist with accountId", action = StateChangeAction.SETUP)
    public void withAccountId_getAccountTransactions_returnsExpectedTransactions() {
    }

    @State(value = "transactions exist with accountId", action = StateChangeAction.TEARDOWN)
    public void withAccountId_getAccountTransactions_returnsExpectedTransactions_TearDown() {
        // Your teardown code here
    }

    @State(
            value = "transactions exist with accountId and date equal or greater than fromDate",
            action = StateChangeAction.SETUP)
    public void withAccountIdAndFromDate_getAccountTransactions_returnsExpectedTransaction() {
    }

    @State(
            value = "transactions exist with accountId and date equal or greater than fromDate",
            action = StateChangeAction.TEARDOWN)
    public void
    withAccountIdAndFromDate_getAccountTransactions_returnsExpectedTransaction_Teardown() {
        // Your teardown code here
    }
}
