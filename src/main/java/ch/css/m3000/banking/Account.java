package ch.css.m3000.banking;

import ch.css.m3000.banking.adapter.DateTimeAdapter;
import ch.css.m3000.banking.adapter.SystemDateTimeAdapter;

import java.time.LocalDate;

public class Account {

    public static final String HEADER = "Date       Amount   Balance";
    private final BankingTransactions transactions = new BankingTransactions();

    private final DateTimeAdapter dateTimeAdapter;

    public Account() {
        this(new SystemDateTimeAdapter());
    }

    public Account(final DateTimeAdapter dateTimeAdapter) {
        this.dateTimeAdapter = dateTimeAdapter;
    }

    private static boolean isNotEnoughMoneyOnTheAccount(final int amount, final int currentTotal) {
        return currentTotal - amount < 0;
    }

    public String printStatement() {
        var transactionText = new StringBuilder();
        var currentTotal = 0;
        for (BankingTransaction transaction : transactions) {
            currentTotal = transaction.calculateNewTotal(currentTotal);
            transactionText.append("\n%s %s    %d".formatted(transaction.dateText(), transaction.amountText(), currentTotal));
        }
        return HEADER + transactionText;
    }

    public void deposit(final int amount) {
        LocalDate depositDate = dateTimeAdapter.currentDate();
        this.transactions.addDeposit(amount, depositDate);
    }

    public void withdraw(final int amount) {
        validateWithdraw(amount);
        LocalDate depositDate = dateTimeAdapter.currentDate();
        this.transactions.addWithdraw(amount, depositDate);
    }

    private void validateWithdraw(final int amount) {
        if (isNotEnoughMoneyOnTheAccount(amount, transactions.currentTotal())) {
            throw new NotEnoughMoneyOnBankAccountException();
        }
    }
}
