package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    boolean createTransfer(Account sender, Account receiver, String amount);
    void updateTransferStatus(Transfer transfer, String status);
    Transfer getTransferById(int id);
    List<User> findAllTransfers();
}
