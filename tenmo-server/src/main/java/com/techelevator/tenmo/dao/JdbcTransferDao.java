package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private final JdbcTemplate jdbcTemplate;
    private JdbcUserDao jdbcUserDao;
    private JdbcAccountDao jdbcAccountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, JdbcUserDao jdbcUserDao, JdbcAccountDao jdbcAccountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserDao = jdbcUserDao;
        this.jdbcAccountDao = jdbcAccountDao;
    }

    @Override
    public boolean createTransfer(String sender, String receiver, String amount) {
        User senderAsUser = jdbcUserDao.findByUsername(sender);
        User receiverAsUser = jdbcUserDao.findByUsername(receiver);
        if (senderAsUser.equals(receiverAsUser)) {
            return false;
        }
        //TODO add try catch for big decimal and positive
        BigDecimal amountAsBigDecimal = new BigDecimal(amount);

        String sql = "INSERT INTO transfer (sender, receiver, amount, transfer_status) VALUES (?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql, senderAsUser.getId(), receiverAsUser.getId(), amountAsBigDecimal, "pending");
            return true;
        } catch (DataAccessException exception) {
            return false;
        }
    }

    @Override
    public Transfer getTransferById(int id, String username) {
        User user = jdbcUserDao.findByUsername(username);
        //TODO make sure this select statement works.
        String sql = "SELECT sender, receiver, amount, transfer_status, transfer_id FROM transfer WHERE transfer_id = ? AND (sender = ? OR receiver = ?)";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id, user.getId(), user.getId());
            if (results.next()) {
                return mapRowToTransfer(results);
            }
        } catch (DataAccessException e) {
            System.out.println("Access exception caught : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Transfer> findAllTransfers(String username) {
        User user = jdbcUserDao.findByUsername(username);
        List<Transfer> transfers = new ArrayList<>();

        String sql = "SELECT sender, receiver, amount, transfer_status, transfer_id FROM transfer WHERE sender = ? OR receiver = ?";


        //TODO verify try catch
       try {
           SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user.getId(), user.getId());
           while (results.next()) {
               transfers.add(mapRowToTransfer(results));
           }
       } catch (DataAccessException e) {
           System.out.println("Access exception caught : " + e.getMessage());
           return null;
       }
        return transfers;
    }
    public boolean finalizeTransfer(int id, String status, String sender) {
        if (!isValidStatus(status)) {
            return false;
        }
        Transfer transfer = getTransferById(id, sender);
        if (status.equals("rejected")) {
            return updateStatus(id, status, transfer.getSender());
        }
        BigDecimal newSenderBalance = jdbcAccountDao.getBalance(sender);
        if (!hasEnoughMoney(transfer.getAmount(), newSenderBalance)) {
            return false;
        }
        // prepare accounts
        Account senderAccount = new Account(newSenderBalance,  )

    }
    private boolean hasEnoughMoney(BigDecimal transferAmount, BigDecimal balance) {
        return balance.subtract(transferAmount).compareTo(BigDecimal.ZERO) >= 0;
    }
    private boolean updateStatus (int transferId, String newStatus, int userId) {
        String sql = "UPDATE transfer SET transfer_status = ? WHERE transfer_id = ? AND sender = ?";
        try {
            jdbcTemplate.update(sql, newStatus, transferId, userId);
        } catch (DataAccessException exception) {
            return false;
        }
        return true;
    }
    private boolean isValidStatus(String status) {
        if (status.equals("approved") || status.equals("rejected")) {
            return true;
        }
        return false;
    }
//
//    @Override
//    public boolean updateTransferStatus(int id, String status, String sender) {
//        if (status.equals("approved")) {
//            System.out.println("here");
//            if (!makeTransfer(id, sender));
//            status = "pending";
//        }
//        System.out.println("there");
//        User user = jdbcUserDao.findByUsername(sender);
//        //TODO verify sql statement
//        String sql = "UPDATE transfer SET transfer_status = ? WHERE transfer_id = ? AND sender = ?";
//        try {
//            jdbcTemplate.update(sql, status, id, user.getId());
//        } catch (DataAccessException exception) {
//            return false;
//        }
//        return true;
//    }

//    @Override
//    public List<Transfer> findAllPendingTransfers(String username) {
//        User user = jdbcUserDao.findByUsername(username);
//        List<Transfer> transfers = new ArrayList<>();
//
//        String sql = "SELECT sender, receiver, amount, transfer_status, transfer_id FROM transfer WHERE (sender = ? OR receiver = ?) AND transfer_status = pending";
//
//
//        //TODO verify try catch
//        try {
//            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user.getId(), user.getId());
//            while (results.next()) {
//                transfers.add(mapRowToTransfer(results));
//            }
//        } catch (DataAccessException e) {
//            System.out.println("Access exception caught : " + e.getMessage());
//            return null;
//        }
//        return transfers;
//    }

//    private boolean makeTransfer (int id, String sender) {
//        // TODO verify if purchase can be made, if so update account balances
//       Transfer transfer = getTransferById(id, sender);
//        // if sender does not have enough money return false
//        String sql = "SELECT balance FROM account WHERE user_id = ?";
//        //TODO should we add try catch here
//        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, transfer.getSender()); // holds sender's balance
//        if (balance.subtract(transfer.getAmount()).compareTo(BigDecimal.ZERO) >= 0) {
//            // sender updates balance with negative balance amount
//            jdbcAccountDao.updateBalance(transfer.getSender(), transfer.getAmount().multiply(new BigDecimal("-1")));
//            jdbcAccountDao.updateBalance(transfer.getReceiver(), transfer.getAmount());
//            return true;
//        }
//        return false;
//    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setSender(rs.getInt("sender"));
        transfer.setReceiver(rs.getInt("receiver"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        transfer.setStatus(rs.getString("transfer_status"));
        transfer.setId(rs.getInt("transfer_id"));

        return transfer;
    }
}
