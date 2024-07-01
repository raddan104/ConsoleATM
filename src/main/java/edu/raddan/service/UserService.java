package edu.raddan.service;

import edu.raddan.utils.CardNumberGenerator;

import java.io.*;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

public class UserService {

    private static final String ACCOUNTS_FILE = "accounts.txt";

    public synchronized void updateUsersAndAccounts() {
        try (BufferedWriter accountsWriter = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (int i = 0; i < 4; i++) {
                String cardNumber = CardNumberGenerator.generateRandomString();
                String PIN = String.valueOf(ThreadLocalRandom.current().nextLong(1000, 10000));
                BigDecimal balance = new BigDecimal("10000.00");

                accountsWriter.write(cardNumber + " " + PIN + " " + balance + " false -1");
                accountsWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to accounts file: " + e.getMessage());
        }
    }
}
