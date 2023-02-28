package com.retail.customer.rewards.CustomerRewards.service.impl;

import com.retail.customer.rewards.CustomerRewards.entity.Customer;
import com.retail.customer.rewards.CustomerRewards.model.CustomerDTO;
import com.retail.customer.rewards.CustomerRewards.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private CustomerRepository respository;
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void testCheckIfCustomerPresentWhenCustomerNotPresent() {
        Mockito.when(respository.findById(123L)).thenReturn(Optional.empty());
        assertTrue(customerService.checkIfCustomerNotPresent(123L));
    }

    @Test
    void testCheckIfCustomerPresentWhenCustomerPresent() {
        Mockito.when(respository.findById(123L)).thenReturn(Optional.of(new Customer()));
        assertFalse(customerService.checkIfCustomerNotPresent(123L));
    }

    @Test
    void testGetAllCustomersWhenNoCustomersPresent() {
        Mockito.when(respository.findAll()).thenReturn(new ArrayList<>());
        assertEquals(0, customerService.getAllCustomers().size());
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new Customer();
        customer1.setCustomerId(1L);
        customer1.setCustomerName("John Doe");
        Customer customer2 = new Customer();
        customer2.setCustomerId(2L);
        customer2.setCustomerName("Sarah Smith");
        customers.add(customer1);
        customers.add(customer2);
        Mockito.when(respository.findAll()).thenReturn(customers);
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();
        assertEquals(2, customerDTOS.size());
    }

}