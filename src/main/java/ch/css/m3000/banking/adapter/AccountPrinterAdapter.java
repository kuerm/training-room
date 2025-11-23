package ch.css.m3000.banking.adapter;

import ch.css.m3000.banking.domain.BankingTransaction;
import ch.css.m3000.banking.domain.BankingTransactions;
import ch.css.m3000.banking.domain.PrinterAdapter;

public class AccountPrinterAdapter implements PrinterAdapter {

    private static final String HEADER = "Date       Amount   Balance";

    public String print(BankingTransactions transactions) {
        var transactionText = new StringBuilder();
        var currentTotal = 0;
        for (BankingTransaction transaction : transactions) {
            currentTotal = transaction.calculateNewTotal(currentTotal);
            transactionText.append("\n%s %s    %d".formatted(transaction.dateText(), transaction.amountText(), currentTotal));
        }
        return HEADER + transactionText;
    }
}
