package ch.css.m3000.banking.application;

import ch.css.m3000.banking.adapter.AccountPrinterAdapter;
import ch.css.m3000.banking.adapter.DateTimeAdapter;
import ch.css.m3000.banking.adapter.SystemDateTimeAdapter;
import ch.css.m3000.banking.domain.BankingTransactions;
import ch.css.m3000.banking.domain.NotEnoughMoneyOnBankAccountException;
import ch.css.m3000.banking.domain.PrinterAdapter;

import java.time.LocalDate;

public class Account {

    private final BankingTransactions transactions = new BankingTransactions();

    private final DateTimeAdapter dateTimeAdapter;
    private final PrinterAdapter accountPrinter = new AccountPrinterAdapter();

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
        return accountPrinter.print(transactions);
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
