package com.retail.customer.rewards.CustomerRewards.repository;

import com.retail.customer.rewards.CustomerRewards.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
