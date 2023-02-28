package com.retail.customer.rewards.CustomerRewards.model;

import lombok.Data;

@Data
public class Rewards {
    private long customerId;
    private long firstMonthRewardPoints;
    private long secondMonthRewardPoints;
    private long thirdMonthRewardPoints;
    private long totalRewardPoints;
}
