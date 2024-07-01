package edu.raddan.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {

    private String cardNumber;
    private BigDecimal balance;
    private Boolean isLocked;
    private LocalDateTime instance;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public LocalDateTime getInstance() {
        return instance;
    }

    public void setInstance(LocalDateTime instance) {
        this.instance = instance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
