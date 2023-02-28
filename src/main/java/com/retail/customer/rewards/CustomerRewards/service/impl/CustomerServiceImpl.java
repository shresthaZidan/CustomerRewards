package com.retail.customer.rewards.CustomerRewards.service.impl;

import com.retail.customer.rewards.CustomerRewards.entity.Customer;
import com.retail.customer.rewards.CustomerRewards.model.CustomerDTO;
import com.retail.customer.rewards.CustomerRewards.repository.CustomerRepository;
import com.retail.customer.rewards.CustomerRewards.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean checkIfCustomerNotPresent(Long customerId) {
        return customerRepository.findById(customerId).isEmpty();
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerEntities = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customerEntities.forEach(customer -> {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getCustomerId());
            customerDTO.setName(customer.getCustomerName());
            customerDTOS.add(customerDTO);
        });
        return customerDTOS;
    }
}
