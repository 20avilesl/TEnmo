package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    boolean createTransfer(Transfer transfer);
    boolean updateTransferStatus(int id, String status);
    Transfer getTransferById(int id, User user);
    List<Transfer> findAllTransfers(String username);
}
