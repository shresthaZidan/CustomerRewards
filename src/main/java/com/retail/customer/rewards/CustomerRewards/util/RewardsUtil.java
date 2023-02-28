package com.retail.customer.rewards.CustomerRewards.util;

import com.retail.customer.rewards.CustomerRewards.entity.Transaction;

import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.List;

public class RewardsUtil {

    private static final int BASE_REWARD_EXPENSE_AMOUNT = 50;
    private static final int EXTRA_REWARD_EXPENSE_THRESHHOLD = 100;

    public static Timestamp getDateBasedOnOffSetDays(int month, boolean end) {
        if (end) {
            return Timestamp.valueOf(YearMonth.now().atDay(1).minusMonths(month - 1).atStartOfDay().minusSeconds(1));
        } else {
            return Timestamp.valueOf(YearMonth.now().atDay(1).minusMonths(month).atStartOfDay());
        }
    }

    public static Long getRewardsPerMonth(List<Transaction> transactions) {
        return transactions.stream().map(RewardsUtil::calculateRewards).mapToLong(reward -> reward).sum();
    }

    private static Long calculateRewards(Transaction individualTransaction) {
        if (individualTransaction.getTransactionAmount() > BASE_REWARD_EXPENSE_AMOUNT && individualTransaction.getTransactionAmount() <= EXTRA_REWARD_EXPENSE_THRESHHOLD) {
            return Math.round(individualTransaction.getTransactionAmount() - BASE_REWARD_EXPENSE_AMOUNT);
        } else if (individualTransaction.getTransactionAmount() > EXTRA_REWARD_EXPENSE_THRESHHOLD) {
            return Math.round(individualTransaction.getTransactionAmount() - EXTRA_REWARD_EXPENSE_THRESHHOLD) * 2
                    + (EXTRA_REWARD_EXPENSE_THRESHHOLD - BASE_REWARD_EXPENSE_AMOUNT);
        } else
            return 0L;
    }
}
