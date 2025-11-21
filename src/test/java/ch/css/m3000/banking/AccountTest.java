package ch.css.m3000.banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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
    void printStatementWhenOneDepositAndOneWithdrawHappenThenPrintThem() {
        testee.deposit(200);
        testee.withdraw(100);

        String actual = testee.printStatement();

        assertThat(actual).isEqualTo(""" 
                Date       Amount   Balance
                21.11.2025 +200    200
                21.11.2025 -100    100""");
    }

    @Test
    void printStatementWhenNoDepositButWithdrawThenThrowException() {
        assertThatExceptionOfType(NotEnoughMoneyOnBankAccountException.class).isThrownBy(() -> testee.withdraw(5));
    }

    @Test
    void printStatementWhenTwoDepositHappenOnDifferentDatesThenPrintWithDeposit() {
        DateTimeAdapter fakeDateTimeAdapter = new FakeDateTimeAdapter(
                LocalDate.of(2025, Month.NOVEMBER, 21),
                LocalDate.of(2025, Month.DECEMBER, 22));
        var testee = new Account(fakeDateTimeAdapter);
        testee.deposit(200);
        testee.deposit(300);

        String actual = testee.printStatement();

        assertThat(actual).isEqualTo(""" 
                Date       Amount   Balance
                21.11.2025 +200    200
                22.12.2025 +300    500""");
    }


    private class FakeDateTimeAdapter implements DateTimeAdapter {
        private final LocalDate[] dates;
        private int i = 0;

        public FakeDateTimeAdapter(LocalDate... dates) {
            this.dates = dates;
        }

        @Override
        public LocalDate currentDate() {
            return dates[i++];
        }
    }
}
