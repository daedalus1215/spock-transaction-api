package com.example.transactionsapi.bootstrap;

import com.example.transactionsapi.entity.Account;
import com.example.transactionsapi.entity.Transaction;
import com.example.transactionsapi.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

import static java.util.Collections.emptySet;

@Profile("!test")
@Component
public class DataLoader implements CommandLineRunner {

    private final AccountRepository accountRepository;

    public DataLoader(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) {
        final Account account = new Account(
                123L,
                "Tony Soprano",
                emptySet()
        );
        final Transaction transaction = new Transaction(
                456L,
                LocalDate.of(2022, 2, 2).toEpochDay(),
                50.00,
                "Amazon",
                "XP Explained Book",
                account
        );
        account.setTransactions(Set.of(transaction));
        accountRepository.save(account);
    }
}
