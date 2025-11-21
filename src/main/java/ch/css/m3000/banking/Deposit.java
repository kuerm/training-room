package ch.css.m3000.banking;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Deposit implements BankingTransaction {
    private final int amount;
    private final LocalDate date;

    public Deposit(int amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String dateText() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(date);
    }

    @Override
    public int calculateNewTotal(final int currentTotal) {
        return currentTotal + amount;
    }

    @Override
    public String amountText() {
        return "+" + amount;
    }
}
