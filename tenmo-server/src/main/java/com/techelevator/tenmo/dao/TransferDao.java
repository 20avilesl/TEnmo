package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    void createTransfer(String sender, String receiver, String amount, String username);
    Transfer getTransferById(int id, String username);
    List<Transfer> findAllTransfers(String username);
    void finalizeTransfer(int id, String status, String sender);
}
