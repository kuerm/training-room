package ch.css.m3000.banking;

import ch.css.m3000.banking.adapter.DateTimeAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final String HEADER = "Date       Amount   Balance";
    private final List<BankingTransaction> transactions = new ArrayList<>();

    private final DateTimeAdapter dateTimeAdapter;

    public Account(final DateTimeAdapter dateTimeAdapter) {
        this.dateTimeAdapter = dateTimeAdapter;
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
        this.transactions.add(new Deposit(amount, depositDate));
    }

    public void withdraw(final int amount) {
        validateWithdraw(amount);
        LocalDate depositDate = dateTimeAdapter.currentDate();
        this.transactions.add(new Withdraw(amount, depositDate));
    }

    private void validateWithdraw(final int amount) {
        int currentTotal = 0;
        for (BankingTransaction transaction : transactions) {
            currentTotal = transaction.calculateNewTotal(currentTotal);
        }
        if (currentTotal - amount < 0) {
            throw new NotEnoughMoneyOnBankAccountException();
        }
    }
}
