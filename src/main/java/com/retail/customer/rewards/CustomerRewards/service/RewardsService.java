package com.retail.customer.rewards.CustomerRewards.service;

import com.retail.customer.rewards.CustomerRewards.model.Rewards;
import org.springframework.stereotype.Service;

public interface RewardsService {
    Rewards getRewardsByCustomerId(Long customerId);
}
