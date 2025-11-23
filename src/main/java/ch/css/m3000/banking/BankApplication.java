package ch.css.m3000.banking;

import ch.css.m3000.banking.adapter.DateTimeAdapter;
import ch.css.m3000.banking.adapter.SystemDateTimeAdapter;

import java.util.Scanner;

public class BankApplication {

    static void main() {
        DateTimeAdapter dateTimeAdapter = new SystemDateTimeAdapter();
        Account account = new Account(dateTimeAdapter);
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Banking Application Started");
            System.out.println("Enter transactions (e.g., +200 for deposit, -100 for withdraw, 'quit' to exit):");

            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("quit")) {
                    System.out.println("Exiting application...");
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
                    System.out.println("Invalid format. Use +200 for deposit or -100 for withdraw.");
                } catch (NotEnoughMoneyOnBankAccountException e) {
                    System.out.println("Error: Not enough money on bank account.");
                }
            }
        }
    }

    private static void processTransaction(Account account, String input) {
        if (input.startsWith("+")) {
            int amount = Integer.parseInt(input.substring(1));
            account.deposit(amount);
        } else if (input.startsWith("-")) {
            int amount = Integer.parseInt(input.substring(1));
            account.withdraw(amount);
        } else {
            throw new NumberFormatException("Transaction must start with + or -");
        }
    }

}

