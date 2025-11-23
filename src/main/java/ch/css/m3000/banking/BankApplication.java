package ch.css.m3000.banking;

import ch.css.m3000.banking.adapter.DateTimeAdapter;
import ch.css.m3000.banking.adapter.SystemDateTimeAdapter;

import java.util.Scanner;

public class BankApplication {

    private static final String START_MESSAGE = "Banking Application Started";
    private static final String INSTRUCTIONS = "Enter transactions (e.g., +200 for deposit, -100 for withdraw, 'quit' to exit):";
    private static final String INPUT_PROMPT = "> ";
    private static final String QUIT_COMMAND = "quit";
    private static final String DEPOSIT_PREFIX = "+";
    private static final String WITHDRAW_PREFIX = "-";
    private static final String INVALID_FORMAT_MESSAGE = "Invalid format. Use +200 for deposit or -100 for withdraw.";
    private static final String NOT_ENOUGH_FUNDS_MESSAGE = "Error: Not enough money on bank account.";
    private static final String EXIT_MESSAGE = "Exiting application...";
    private static final String TRANSACTION_PREFIX_ERROR = "Transaction must start with + or -";

    static void main() {
        DateTimeAdapter dateTimeAdapter = new SystemDateTimeAdapter();
        Account account = new Account(dateTimeAdapter);
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(START_MESSAGE);
            System.out.println(INSTRUCTIONS);

            while (true) {
                System.out.print(INPUT_PROMPT);
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase(QUIT_COMMAND)) {
                    System.out.println(EXIT_MESSAGE);
                    break;
                }

                if (input.isEmpty()) {
                    continue;
                }

                try {
                    processTransaction(account, input);
                    System.out.println(account.printStatement());
                    System.out.println();
                } catch (NumberFormatException e) {
                    System.out.println(INVALID_FORMAT_MESSAGE);
                } catch (NotEnoughMoneyOnBankAccountException e) {
                    System.out.println(NOT_ENOUGH_FUNDS_MESSAGE);
                }
            }
        }
    }

    private static void processTransaction(Account account, String input) {
        if (input.startsWith(DEPOSIT_PREFIX)) {
            int amount = Integer.parseInt(input.substring(DEPOSIT_PREFIX.length()));
            account.deposit(amount);
        } else if (input.startsWith(WITHDRAW_PREFIX)) {
            int amount = Integer.parseInt(input.substring(WITHDRAW_PREFIX.length()));
            account.withdraw(amount);
        } else {
            throw new NumberFormatException(TRANSACTION_PREFIX_ERROR);
        }
    }

}
