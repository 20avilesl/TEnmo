package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
    private BigDecimal balance;
    private int accountId;
    private long userId;

    public Account(){

    }

    public Account(BigDecimal balance, int accountId, int userId) {
        this.balance = balance;
        this.accountId = accountId;
        this.userId = userId;

    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public long getUserId() {
        return userId;
    }
}
