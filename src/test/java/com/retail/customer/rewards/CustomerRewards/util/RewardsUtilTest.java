package com.retail.customer.rewards.CustomerRewards.util;

import com.retail.customer.rewards.CustomerRewards.entity.Transaction;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RewardsUtilTest {

    @Test
    void testGetDateBasedOnOffSetDaysStartOffSetByOne() {
        Timestamp timestamp = RewardsUtil.getDateBasedOnOffSetDays(1, false);
        assertNotNull(timestamp);
        assertEquals(1, timestamp.toLocalDateTime().getDayOfMonth());
        assertEquals(LocalDateTime.now().minusMonths(1).getMonth(), timestamp.toLocalDateTime().getMonth());
    }

    @Test
    void testGetDateBasedOnOffSetDaysEndOffSetByOne() {
        Timestamp timestamp = RewardsUtil.getDateBasedOnOffSetDays(2, true);
        assertNotNull(timestamp);
        assertEquals(YearMonth.now().atDay(1).minusMonths(1).atStartOfDay().minusSeconds(1).getDayOfMonth(), timestamp.toLocalDateTime().getDayOfMonth());
        assertEquals(LocalDateTime.now().minusMonths(2).getMonth(), timestamp.toLocalDateTime().getMonth());
    }


    @Test
    void testGetRewardsPerMonthWhenNoTransaction() {
        assertEquals(0, RewardsUtil.getRewardsPerMonth(new ArrayList<>()));
    }

    @Test
    void testGetRewardsPerMonthWhenOneTransaction() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(123L, 1L, Timestamp.valueOf(YearMonth.now().atDay(1).atStartOfDay()), 130));
        assertEquals(110, RewardsUtil.getRewardsPerMonth(transactions));
    }
 @Test
    void testGetRewardsPerMonthWhenTwoTransaction() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(123L, 1L, Timestamp.valueOf(YearMonth.now().atDay(1).atStartOfDay()), 130));
        transactions.add(new Transaction(124L, 1L, Timestamp.valueOf(YearMonth.now().atDay(1).atStartOfDay()), 65));
        assertEquals(125, RewardsUtil.getRewardsPerMonth(transactions));
    }
 @Test
    void testGetRewardsPerMonthWhenTransactionsBelowRewardsLimit() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(123L, 1L, Timestamp.valueOf(YearMonth.now().atDay(1).atStartOfDay()), 49));
        transactions.add(new Transaction(124L, 1L, Timestamp.valueOf(YearMonth.now().atDay(1).atStartOfDay()), 20));
        assertEquals(0, RewardsUtil.getRewardsPerMonth(transactions));
    }

}