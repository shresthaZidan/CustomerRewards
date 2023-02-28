package com.retail.customer.rewards.CustomerRewards.controller;

import com.retail.customer.rewards.CustomerRewards.model.CustomerDTO;
import com.retail.customer.rewards.CustomerRewards.model.Rewards;
import com.retail.customer.rewards.CustomerRewards.service.CustomerService;
import com.retail.customer.rewards.CustomerRewards.service.RewardsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class RewardsControllerTest {
    @Mock
    private RewardsService rewardsService;
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private RewardsController controller;

    @Test
    void testGetAllCustomers() {
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setId(1L);
        customerDTO1.setName("John Doe");
        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setId(2L);
        customerDTO2.setName("Sarah Smith");
        customerDTOs.add(customerDTO1);
        customerDTOs.add(customerDTO2);
        Mockito.when(customerService.getAllCustomers()).thenReturn(customerDTOs);
        ResponseEntity<List<CustomerDTO>> responseEntity = controller.getAllCustomers();
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        Assertions.assertEquals(2, responseEntity.getBody().size());
    }

    @GetMapping(value = "/{customerId}/rewards", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rewards> getRewardsByCustomerId(@PathVariable Long customerId) {
        if (customerService.checkIfCustomerNotPresent(customerId)) {
            return ResponseEntity.badRequest().build();
        }
        Rewards customerRewards = rewardsService.getRewardsByCustomerId(customerId);
        return new ResponseEntity<>(customerRewards, HttpStatus.OK);
    }

    @Test
    void testGetRewardsByCustomerIdWhenCustomerIdNotPresent() {
        Mockito.when(customerService.checkIfCustomerNotPresent(1L)).thenReturn(true);
        Assertions.assertEquals(400, controller.getRewardsByCustomerId(1L).getStatusCode().value());
    }

    @Test
    void testGetRewardsByCustomerIdWhenCustomerMatches() {
        Mockito.when(customerService.checkIfCustomerNotPresent(1L)).thenReturn(false);
        Rewards rewards = new Rewards();
        rewards.setFirstMonthRewardPoints(200);
        rewards.setSecondMonthRewardPoints(30);
        rewards.setThirdMonthRewardPoints(150);
        rewards.setCustomerId(1L);
        Mockito.when(rewardsService.getRewardsByCustomerId(1L)).thenReturn(rewards);
        ResponseEntity<Rewards> responseEntity = controller.getRewardsByCustomerId(1L);
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        Assertions.assertEquals(rewards, responseEntity.getBody());
    }
}