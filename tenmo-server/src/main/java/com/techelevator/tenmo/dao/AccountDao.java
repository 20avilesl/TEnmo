package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {
   // Account getAccount(Long id);
    void updateBalance(int user, BigDecimal amount);
}

