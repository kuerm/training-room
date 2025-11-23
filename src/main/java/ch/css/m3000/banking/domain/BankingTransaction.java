package ch.css.m3000.banking.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface BankingTransaction {
    default String dateText() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(date());
    }

    String amountText();

    LocalDate date();

    int calculateNewTotal(int currentTotal);
}
