package edu.raddan.service;

import edu.raddan.entity.Account;

import java.math.BigDecimal;

public interface ATMOperations {
    void checkBalance(String cardNumber);
    void withdraw(String cardNumber, BigDecimal amount);
    void deposit(String cardNumber, BigDecimal amount);
}
