package com.retail.customer.rewards.CustomerRewards.repository;

import com.retail.customer.rewards.CustomerRewards.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findCustomersByCustomerId(Long customerId);

    List<Transaction> findAllByCustomerIdAndTransactionDateBetween(Long customerId, Timestamp timestampFrom, Timestamp timestampTo);
}
