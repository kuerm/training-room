package ch.css.m3000.banking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class BankingTransactions implements Iterable<BankingTransaction> {

    private final List<BankingTransaction> transactions = new ArrayList<>();

    @Override
    public Iterator<BankingTransaction> iterator() {
        return transactions.iterator();
    }

    public void addDeposit(final int amount, final LocalDate depositDate) {
        transactions.add(new Deposit(amount, depositDate));
    }

    public void addWithdraw(final int amount, final LocalDate depositDate) {
        transactions.add(new Withdraw(amount, depositDate));
    }

    public int currentTotal() {
        int currentTotal = 0;
        for (BankingTransaction transaction : transactions) {
            currentTotal = transaction.calculateNewTotal(currentTotal);
        }
        return currentTotal;
    }
}

