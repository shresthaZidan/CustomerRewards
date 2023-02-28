package com.retail.customer.rewards.CustomerRewards.service;

import com.retail.customer.rewards.CustomerRewards.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    boolean checkIfCustomerNotPresent(Long customerId);
    List<CustomerDTO> getAllCustomers();
}
