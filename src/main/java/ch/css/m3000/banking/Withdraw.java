package ch.css.m3000.banking;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Withdraw(int amount, LocalDate date) implements BankingTransaction {
    @Override
    public String amountText() {
        return "-" + amount;
    }

    @Override
    public String dateText() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(date);
    }

    @Override
    public int calculateNewTotal(final int currentTotal) {
        return currentTotal - amount;
    }
}
