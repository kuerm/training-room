package ch.css.m3000.banking;

public interface BankingTransaction {
    String amountText();

    String dateText();

    int calculateNewTotal(int currentTotal);
}
