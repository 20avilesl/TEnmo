package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {
    // Account getAccount(Long id);
    void updateBalance(Account account);
    BigDecimal getBalance(String username);
    BigDecimal getBalance(int id);
}

