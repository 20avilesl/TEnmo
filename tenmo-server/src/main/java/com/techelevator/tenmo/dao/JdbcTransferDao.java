package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDao implements TransferDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public boolean createTransfer(Account sender, Account receiver, String amount) {
        String sql = "INSERT INTO transfer (sender, receiver, amount, date_sent, transfer_status VALUES (?, ?, ?, ?, ?)";
        //TODO change date
        //TODO review this try catch

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, sender.getUserId(), receiver.getUserId(), new BigDecimal(amount), "10-10-2022", "pending");
            while (results.next()) {
                Transfer transfer = mapRowToTransfer(results);
            }
        } catch (DataAccessException exception) {
            return false;
        }
        return true;
    }

    @Override
    public void updateTransferStatus(Transfer transfer, String status) {
        String sql = "UPDATE transfer SET transfer_status = ? WHERE transfer_id = ?";
        jdbcTemplate.update(sql, status, transfer.getId());
    }

    @Override
    public Transfer getTransferById(int id) {
        return null;
    }

    @Override
    public List<User> findAllTransfers() {
        // TODO do we need to pass in a user?
        return null;
    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setSender(rs.getInt("sender"));
        transfer.setReceiver(rs.getInt("receiver"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        transfer.setDate(rs.getDate("date"));
        transfer.setStatus(rs.getString("transfer_status"));

        return transfer;
    }
}
