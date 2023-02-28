package com.retail.customer.rewards.CustomerRewards.service.impl;

import com.retail.customer.rewards.CustomerRewards.entity.Transaction;
import com.retail.customer.rewards.CustomerRewards.model.Rewards;
import com.retail.customer.rewards.CustomerRewards.repository.TransactionRepository;
import com.retail.customer.rewards.CustomerRewards.util.RewardsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class RewardsServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private RewardsServiceImpl rewardsService;

    @Test
    void testGetRewardsForCustomer() {
        setUpFirstMonthTransaction();
        setUpSecondMonthTransaction();
        setUpThirdMonthTransaction();
        Rewards rewards = new Rewards();
        rewards.setCustomerId(1L);
        rewards.setFirstMonthRewardPoints(40);
        rewards.setSecondMonthRewardPoints(170);
        rewards.setThirdMonthRewardPoints(150);
        rewards.setTotalRewardPoints(360);
        Assertions.assertEquals(rewards, rewardsService.getRewardsByCustomerId(1L));
    }

    private void setUpFirstMonthTransaction() {
        Timestamp previousToPreviousMonthStartDate = RewardsUtil.getDateBasedOnOffSetDays(2, false);
        Timestamp previousToPreviousMonthEndDate = RewardsUtil.getDateBasedOnOffSetDays(2, true);
        Transaction transactionThirdMonth = new Transaction();
        transactionThirdMonth.setCustomerId(1L);
        transactionThirdMonth.setTransactionAmount(90);
        transactionThirdMonth.setTransactionId(1234L);
        Mockito.when(transactionRepository.findAllByCustomerIdAndTransactionDateBetween(1L, previousToPreviousMonthStartDate, previousToPreviousMonthEndDate)).thenReturn(List.of(transactionThirdMonth));

    }

    private void setUpSecondMonthTransaction() {
        Timestamp previousMonthStartDate = RewardsUtil.getDateBasedOnOffSetDays(1, false);
        Timestamp previousMonthEndDate = RewardsUtil.getDateBasedOnOffSetDays(1, true);
        Transaction transactionSecondMonth = new Transaction();
        transactionSecondMonth.setCustomerId(1L);
        transactionSecondMonth.setTransactionAmount(160);
        transactionSecondMonth.setTransactionId(123L);
        Mockito.when(transactionRepository.findAllByCustomerIdAndTransactionDateBetween(1L, previousMonthStartDate, previousMonthEndDate)).thenReturn(List.of(transactionSecondMonth));

    }

    private void setUpThirdMonthTransaction() {
        Timestamp currentMonthStartDate = RewardsUtil.getDateBasedOnOffSetDays(0, false);
        Timestamp currentMonthEndDate = RewardsUtil.getDateBasedOnOffSetDays(0, true);
        Transaction transactionFirstMonth = new Transaction();
        transactionFirstMonth.setCustomerId(1L);
        transactionFirstMonth.setTransactionAmount(150);
        transactionFirstMonth.setTransactionId(12L);
        Mockito.when(transactionRepository.findAllByCustomerIdAndTransactionDateBetween(1L, currentMonthStartDate, currentMonthEndDate)).thenReturn(List.of(transactionFirstMonth));
    }

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