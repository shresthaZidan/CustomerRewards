package com.retail.customer.rewards.CustomerRewards.service.impl;

import com.retail.customer.rewards.CustomerRewards.entity.Transaction;
import com.retail.customer.rewards.CustomerRewards.model.Rewards;
import com.retail.customer.rewards.CustomerRewards.repository.TransactionRepository;
import com.retail.customer.rewards.CustomerRewards.service.RewardsService;
import com.retail.customer.rewards.CustomerRewards.util.RewardsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RewardsServiceImpl implements RewardsService {
    private TransactionRepository transactionRepository;

    @Autowired
    public RewardsServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Override
    public Rewards getRewardsByCustomerId(Long customerId) {
        Timestamp currentMonthStartDate = RewardsUtil.getDateBasedOnOffSetDays(0, false);
        Timestamp currentMonthEndDate = RewardsUtil.getDateBasedOnOffSetDays(0, true);
        Timestamp previousMonthStartDate = RewardsUtil.getDateBasedOnOffSetDays(1, false);
        Timestamp previousMonthEndDate = RewardsUtil.getDateBasedOnOffSetDays(1, true);
        Timestamp previousToPreviousMonthStartDate = RewardsUtil.getDateBasedOnOffSetDays(2, false);
        Timestamp previousToPreviousMonthEndDate = RewardsUtil.getDateBasedOnOffSetDays(2, true);

        List<Transaction> thirdMonthTransactions = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(
                customerId, currentMonthStartDate, currentMonthEndDate);
        List<Transaction> secondMonthTransactions = transactionRepository
                .findAllByCustomerIdAndTransactionDateBetween(customerId, previousMonthStartDate, previousMonthEndDate);
        List<Transaction> firstMonthTransactions = transactionRepository
                .findAllByCustomerIdAndTransactionDateBetween(customerId, previousToPreviousMonthStartDate,
                        previousToPreviousMonthEndDate);

        Long thirdMonthRewardPoints = RewardsUtil.getRewardsPerMonth(thirdMonthTransactions);
        Long secondMonthRewardPoints = RewardsUtil.getRewardsPerMonth(secondMonthTransactions);
        Long firstMonthRewardPoints = RewardsUtil.getRewardsPerMonth(firstMonthTransactions);

        Rewards rewards = new Rewards();
        rewards.setCustomerId(customerId);
        rewards.setThirdMonthRewardPoints(thirdMonthRewardPoints);
        rewards.setSecondMonthRewardPoints(secondMonthRewardPoints);
        rewards.setFirstMonthRewardPoints(firstMonthRewardPoints);
        rewards.setTotalRewardPoints(thirdMonthRewardPoints + secondMonthRewardPoints + firstMonthRewardPoints);

        return rewards;
    }


}
