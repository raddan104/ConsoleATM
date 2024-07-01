package edu.raddan.service;

import edu.raddan.entity.Account;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Since nothing has been announced about the account registration, let it be randomized
 */
public class ATMService implements ATMOperations {

    private static final String ACCOUNTS_FILE = "accounts.txt";

    @Override
    public void checkBalance(String cardNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 3 && parts[0].equals(cardNumber)) {
                    BigDecimal balance = new BigDecimal(parts[2]);
                    System.out.println("Balance: " + balance);
                    return;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts file: " + e.getMessage());
        }
        System.out.println("Balance: 0.00");
    }

    @Override
    public synchronized void withdraw(String cardNumber, BigDecimal amount) {
        List<String> updatedLines = new CopyOnWriteArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 5 && parts[0].equals(cardNumber)) {
                    BigDecimal balance = new BigDecimal(parts[2]);
                    if (balance.compareTo(amount) >= 0) {
                        BigDecimal newBalance = balance.subtract(amount);
                        line = parts[0] + " " + parts[1] + " " + newBalance + " " + parts[3] + " " + parts[4];
                        System.out.println("Withdrawal successful! New balance: " + newBalance);
                    } else {
                        System.err.println("Insufficient funds to withdraw " + amount + ". Current balance: " + balance);
                        return;
                    }
                }
                updatedLines.add(line); // Add line unchanged or modified
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts file: " + e.getMessage());
            return;
        }

        writeUpdatedLines(updatedLines);
    }

    @Override
    public synchronized void deposit(String cardNumber, BigDecimal amount) {
        List<String> updatedLines = new CopyOnWriteArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 3 && parts[0].equals(cardNumber)) {
                    BigDecimal balance = new BigDecimal(parts[2]);
                    BigDecimal newBalance = balance.add(amount);
                    line = parts[0] + " " + parts[1] + " " + newBalance + " " + parts[3] + " " + parts[4];
                    System.out.println("Deposit successful! New balance: " + newBalance);
                }
                updatedLines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts file: " + e.getMessage());
            return;
        }

        writeUpdatedLines(updatedLines);
    }

    private void writeUpdatedLines(List<String> updatedLines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to accounts file: " + e.getMessage());
        }
    }

}
