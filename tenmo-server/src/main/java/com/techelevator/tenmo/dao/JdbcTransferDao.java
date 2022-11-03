package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private final JdbcTemplate jdbcTemplate;
    private final JdbcUserDao jdbcUserDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, JdbcUserDao jdbcUserDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserDao = jdbcUserDao;
    }

    @Override
    public boolean createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer (sender, receiver, amount, transfer_status, transfer_id) VALUES (?, ?, ?, ?, ?)";

        //TODO change date
        //TODO review this try catch

        try {
            jdbcTemplate.update(sql, transfer.getSender(), transfer.getReceiver(), transfer.getAmount(), transfer.getStatus(), transfer.getId());
            return true;
        } catch (DataAccessException exception) {

            System.out.println("Caught an exception : " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateTransferStatus(int id, String status) {
        String sql = "UPDATE transfer SET transfer_status = ? WHERE transfer_id = ?";
        jdbcTemplate.update(sql, status, id);
        return true;
    }

    @Override
    public Transfer getTransferById(int id, User user) {

        //TODO make sure this select statement works.
        String sql = "SELECT sender, receiver, amount, transfer_status FROM transfer WHERE transfer_id = ? AND sender = ? OR receiver = ?";

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
        String sql = "SELECT sender, receiver, amount, transfer_status, transfer_id FROM transfer WHERE sender = ? OR receiver = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user.getId(), user.getId());

        List<Transfer> transfers = new ArrayList<>();

        while (results.next()) {

            transfers.add(mapRowToTransfer(results));
        }

        // TODO do we need to pass in a user?
        return transfers;
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
