package ch.css.m3000.banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Your Task
 * Your bank is tired of its mainframe COBOL accounting software and they hired both of you for a greenfield project in - what a happy coincidence
 * <p>
 * your favorite programming language!
 * Your task is to show them that your TDD-fu and your new-age programming language can cope with good ole’ COBOL!
 * <p>
 * Requirements
 * Write a class Account that offers the following methods void deposit(int) void withdraw(int) String printStatement()
 * <p>
 * An example statement would be:
 * <p>
 * Date        Amount  Balance
 * 24.12.2015   +500      500
 * 23.8.2016    -100      400
 */
public class AccountTest {
    private Account testee;

    @BeforeEach
    void setUp() {
        testee = new Account(new DateTimeAdapter() {
            @Override
            public LocalDate currentDate() {
                return LocalDate.of(2025, Month.NOVEMBER, 21);
            }
        });
    }

    @Test
    void printStatementWhenNoDepositHappenedThenPrintEmptyAccount() {
        String actual = testee.printStatement();

        assertThat(actual).isEqualTo("Date       Amount   Balance");
    }

    @Test
    void printStatementWhenDepositHappenThenPrintWithDeposit() {
        testee.deposit(200);

        String actual = testee.printStatement();

        assertThat(actual).isEqualTo(""" 
                Date       Amount   Balance
                21.11.2025 +200    200""");
    }

    @Test
    void printStatementWhenTwoDepositHappenThenPrintWithDeposit() {
        testee.deposit(200);
        testee.deposit(300);

        String actual = testee.printStatement();

        assertThat(actual).isEqualTo(""" 
                Date       Amount   Balance
                21.11.2025 +200    200
                21.11.2025 +300    500""");
    }

    @Test
    void printStatementWhenTwoDepositHappenOnDifferentDatesThenPrintWithDeposit() {
        DateTimeAdapter dateTimeAdapterMock = mock(DateTimeAdapter.class);
        when(dateTimeAdapterMock.currentDate())
                .thenReturn(LocalDate.of(2025, Month.NOVEMBER, 21))
                .thenReturn(LocalDate.of(2025, Month.DECEMBER, 22));
        var testee = new Account(dateTimeAdapterMock);
        testee.deposit(200);
        testee.deposit(300);

        String actual = testee.printStatement();

        assertThat(actual).isEqualTo(""" 
                Date       Amount   Balance
                21.11.2025 +200    200
                22.12.2025 +300    500""");
    }

    private interface BankingTransaction {
        String amountText();

        String dateText();

        int calculateNewTotal(int currentTotal);
    }

    private record Deposit(int amount, LocalDate date) implements BankingTransaction {

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

    private class Account {

        public static final String HEADER = "Date       Amount   Balance";
        private final List<BankingTransaction> transactions = new ArrayList<>();

        private final DateTimeAdapter dateTimeAdapter;

        private Account(final DateTimeAdapter dateTimeAdapter) {
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
    }
}
