package com.example.transactionsapi.repository;

import com.example.transactionsapi.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findAllByAccount_AccountId(long accountId);

    List<Transaction> findAllByAccountAccountIdAndDateGreaterThanEqual(long accountId, long date);
}