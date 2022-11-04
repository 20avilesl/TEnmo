package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    boolean createTransfer(String sender, String receiver, String amount);
    Transfer getTransferById(int id, String username);
    List<Transfer> findAllTransfers(String username);
    boolean updateTransferStatus(int id, String status, String sender);
    //List<Transfer> findAllPendingTransfers (String username);

}
// you pay (sender of money) someone money
// you request someone to pay money (receiver of money) person is the sender
