package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;
    private final AccountDao accountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, UserDao userDao, AccountDao accountDao ) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
        this.accountDao = accountDao;

    }

    @Override
    public void createTransfer(String sender, String receiver, String amount, String username) {
        // make sure sender or receiver is the logged user
        if (!(sender.equals(username) || receiver.equals(username))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You Are Not Authorized To Preform This Action");
        }
        // verify sender is not receiver
        if (sender.equals(receiver)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You Cannot Make A Transfer With Yourself");
        }

        // verify that users exists when creating user objects through user dao
        User senderAsUser = userDao.findByUsername(sender);
        User receiverAsUser = userDao.findByUsername(receiver);

        //  verify transfer amount
        BigDecimal amountAsBigDecimal = convertToBigDecimal(amount);
        if (amountAsBigDecimal == null || amountAsBigDecimal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Transfer Amount");
        }

        // update transfer table and return the id for future use
        String sql = "INSERT INTO transfer (sender, receiver, amount, transfer_status) VALUES (?, ?, ?, ?) RETURNING transfer_id";
        //get id of transfer
        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, senderAsUser.getId(), receiverAsUser.getId(), amountAsBigDecimal, "pending");
        if (sender.equals(username)) {
            finalizeTransfer(transferId, "approved", username);
        }
    }

    @Override
    public Transfer getTransferById(int id, String username) {
        User user = userDao.findByUsername(username);
        String sql = "SELECT sender, receiver, amount, transfer_status, transfer_id FROM transfer WHERE transfer_id = ? AND (sender = ? OR receiver = ?)";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id, user.getId(), user.getId());
            if (results.next()) {
                return mapRowToTransfer(results);
            }
        } catch (DataAccessException e) {
            System.out.println("Access exception caught : " + e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You Are Not Authorized To Preform This Action");
    }

    @Override
    public List<Transfer> findAllTransfers(String username) {
        User user = userDao.findByUsername(username);
        List<Transfer> transfers = new ArrayList<>();

        String sql = "SELECT sender, receiver, amount, transfer_status, transfer_id FROM transfer WHERE sender = ? OR receiver = ?";
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
    @Override
    public void finalizeTransfer(int id, String status, String sender) {
        // verify status
        if (!isValidStatus(status)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Action is Invalid");
        }
        // create transfer object for use
        Transfer transfer = getTransferById(id, sender);

        // check if transfer is pending
        if (!transfer.getStatus().equals("pending")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Transfer Cannot Be Modified");

        }
        // verify user (logged in user) is sender
        if (!isSender(sender, transfer.getSender())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You Are Not Authorized To Preform This Action");
        }
        if (status.equals("approved")) {
            // store new balance of sender
            BigDecimal senderBalance = accountDao.getBalance(sender);
            // verify if transfer can be made
            if (!hasEnoughMoney(transfer.getAmount(), senderBalance)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Enough Money For Transfer");
            }
            BigDecimal newSenderBalance = senderBalance.subtract(transfer.getAmount());
            // prepare sender account
            Account senderAccount = new Account(newSenderBalance, transfer.getSender());
            // update account of sender into database
            accountDao.updateBalance(senderAccount);
            // prepare receiver account
            BigDecimal receiverBalance = accountDao.getBalance(transfer.getReceiver());
            BigDecimal newReceiverBalance = receiverBalance.add(transfer.getAmount());
            Account receiverAccount = new Account(newReceiverBalance, transfer.getReceiver());
            // update account of receiver into database
            accountDao.updateBalance(receiverAccount);
        }
        // update status
        updateStatus(id, status);
    }
    private BigDecimal convertToBigDecimal(String value) {
        if (value == null) {
            return null;
        }

        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean isValidStatus(String status) {
        if (status.equals("approved") || status.equals("rejected")) {
            return true;
        }
        return false;
    }

    private boolean isSender(String user, int sender) {
        return (userDao.findIdByUsername(user) == sender);
    }
    private boolean hasEnoughMoney(BigDecimal transferAmount, BigDecimal balance) {
        return balance.subtract(transferAmount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void updateStatus (int transferId, String newStatus) {
        String sql = "UPDATE transfer SET transfer_status = ? WHERE transfer_id = ?";
        try {
            jdbcTemplate.update(sql, newStatus, transferId);
        } catch (DataAccessException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An Error Occurred While Updating The Status");
        }
    }

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
