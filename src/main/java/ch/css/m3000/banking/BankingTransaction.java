package ch.css.m3000.banking;

interface BankingTransaction {
    String amountText();

    String dateText();

    int calculateNewTotal(int currentTotal);
}
