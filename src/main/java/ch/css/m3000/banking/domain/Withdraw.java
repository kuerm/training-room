package ch.css.m3000.banking.domain;

import java.time.LocalDate;

public record Withdraw(int amount, LocalDate date) implements BankingTransaction {
    @Override
    public String amountText() {
        return "-" + amount;
    }

    @Override
    public int calculateNewTotal(final int currentTotal) {
        return currentTotal - amount;
    }
}
