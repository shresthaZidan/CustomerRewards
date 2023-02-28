package com.retail.customer.rewards.CustomerRewards.controller;

import com.retail.customer.rewards.CustomerRewards.model.CustomerDTO;
import com.retail.customer.rewards.CustomerRewards.model.Rewards;
import com.retail.customer.rewards.CustomerRewards.service.CustomerService;
import com.retail.customer.rewards.CustomerRewards.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RewardsController, api entry point to restful service for customer and reward
 */
@RestController
@RequestMapping("/v1/customers")
public class RewardsController {
    private RewardsService rewardsService;

    private CustomerService customerService;

    @Autowired
    public RewardsController(RewardsService rewardsService, CustomerService customerService) {
        this.rewardsService = rewardsService;
        this.customerService = customerService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }


    @GetMapping(value = "/{customerId}/rewards", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rewards> getRewardsByCustomerId(@PathVariable Long customerId) {
        if (customerService.checkIfCustomerNotPresent(customerId)) {
            return ResponseEntity.badRequest().build();
        }
        Rewards customerRewards = rewardsService.getRewardsByCustomerId(customerId);
        return new ResponseEntity<>(customerRewards, HttpStatus.OK);
    }
}
