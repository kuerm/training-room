package ch.css.m3000.banking.domain;

import java.time.LocalDate;

public record Deposit(int amount, LocalDate date) implements BankingTransaction {

    @Override
    public int calculateNewTotal(final int currentTotal) {
        return currentTotal + amount;
    }

    @Override
    public String amountText() {
        return "+" + amount;
    }
}
