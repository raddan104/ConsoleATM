package edu.raddan.atm;

import edu.raddan.service.ATMService;
import edu.raddan.service.UserService;
import edu.raddan.utils.CardNumberGenerator;
import edu.raddan.utils.Printer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class ATM {

    private static final Scanner scan = new Scanner(System.in);
    private static final Integer MAX_DEPOSIT = 1_000_000;

    /**
     * The only method to start the engine...
     */
    public void start() {
        UserService userService = new UserService();
        userService.updateUsersAndAccounts();

        System.out.print("Enter card number (from accounts.txt): ");
        String cardNumber = scan.nextLine().trim();
        if (!CardNumberGenerator.isValidNumber(cardNumber)) {
            System.err.println("Checkout your credentials.");
            return;
        }

        System.out.print("Enter your PIN: ");
        String PIN = scan.nextLine().trim();

        boolean authenticated = auth(cardNumber, PIN);
        if (authenticated) {
            System.out.println("Authentication successful!");
            startOperations(cardNumber);
        } else {
            System.err.println("Authentication failed. Please check your credentials.");
        }

    }

    private void startOperations(String cardNumber) {
        ATMService atmService = new ATMService();
        boolean continueOperations = true;

        while (continueOperations) {
            Printer.printBeautifulLabel();
            Printer.printOptions();
            int choose = scan.nextInt();

            switch (choose) {
                case 1:
                    atmService.checkBalance(cardNumber);
                    break;
                case 2:
                    System.out.print("Enter amount you want to withdraw: ");
                    BigDecimal withdrawAmount = scan.nextBigDecimal();
                    atmService.withdraw(cardNumber, withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount you want to deposit: ");
                    BigDecimal depositAmount = scan.nextBigDecimal();

                    if (depositAmount.compareTo(new BigDecimal(MAX_DEPOSIT)) >= 0) {
                        System.err.println("Max deposit is: " + MAX_DEPOSIT);
                        break;
                    }

                    atmService.deposit(cardNumber, depositAmount);
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    continue;
            }

            System.out.println("Continue operations? (yes/no): ");
            String continueOption = scan.next();
            if (!continueOption.equalsIgnoreCase("yes")) {
                continueOperations = false;
            } else {
                int i = 0;
                while (i < 7) {
                    System.out.println(" ");
                    i++;
                }
            }
        }
    }

    /**
     * Synchronized method to authenticate user credentials against accounts.txt.
     *
     * @param cardNumber The card number entered by the user.
     * @param PIN        The PIN entered by the user.
     * @return true if authentication is successful, false otherwise.
     */
    private synchronized boolean auth(String cardNumber, String PIN) {
        Map<String, String> userDetails = new ConcurrentHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    String storedCardNumber = parts[0];
                    String storedPIN = parts[1];
                    userDetails.put(storedCardNumber, storedPIN);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts file: " + e.getMessage());
            return false;
        }

        return userDetails.containsKey(cardNumber) && userDetails.get(cardNumber).equals(PIN);
    }

}
