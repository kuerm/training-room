package ch.css.m3000.banking;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    @Test
    void printStatementWhenNoDepositHappenedThenPrintEmptyAccount() {
        Account testee = new Account();

        String actual = testee.printStatement();

        assertThat(actual).isEqualTo("Date       Amount   Balance");
    }

    @Test
    void printStatementWhenDepositHappenThenPrintWithDeposit() {
        Account testee = new Account();
        testee.deposit(200);

        String actual = testee.printStatement();

        assertThat(actual).isEqualTo(""" 
                Date       Amount   Balance
                21.11.2025 +200    200""");

    }

    private interface BankingTransaction {
    }

    private record Deposit(int amount) implements BankingTransaction {
    }

    private class Account {

        public static final String HEADER = "Date       Amount   Balance";
        private List<Deposit> transactions;

        public String printStatement() {
            return HEADER;
        }

        public void deposit(final int amount) {
            this.transactions.add(new Deposit(amount));
        }
    }
}
