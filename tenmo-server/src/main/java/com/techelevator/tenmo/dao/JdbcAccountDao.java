package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

//    @Override
//    public Account getAccount(Long id) {
//
//        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
//        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
//        if(rowSet.next()){
//            return mapRowToAccount(rowSet);
//        }
//        throw new UsernameNotFoundException("User ID not found.");
//    }

    @Override
    //TODO may need to refactor to update.
    public void updateBalance(int userId, BigDecimal amount) {
        String sql = "SELECT balance FROM account WHERE user_id = ?";
        //TODO should we have try catch here
        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
        balance = balance.add(amount);
        sql = "UPDATE account SET balance = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, balance, userId);
    }
    // -> 1000 -> 980
//    @Override
//    public void updateBalance(Account account) {
//        String sql = "UPDATE account SET BALANCE = ? WHERE account_id = ?";
//        jdbcTemplate.update(sql, account.getBalance(), account.getAccountId());
//    }
 //   }
    @Override
    public int getBalance(String username) {
        String sql = "SELECT balance FROM account as a JOIN tenmo_user as u ON u.user_id = a.user_id WHERE u.username = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, username);
    }


    //"UPDATE transfer SET transfer_status = ? WHERE transfer_id = ? AND sender = ?";
//        String sql = "SELECT sender, receiver, date_sent, transfer_status amount FROM transfer AS t "
//                + "JOIN tenmo_user AS tu ON tu.user_id = t.sender "
//                + "WHERE tu.user_id = ? AND t.transfer_status = 'approved';";
//
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
//        while(results.next()) {
//            Transfer transfer = mapRowToTransfer(results);
//        }
//        // update sender balance
//        // updaete receiver balance
//
//
//        BigDecimal transferAmount = jdbcTemplate.queryForObject(sql, BigDecimal.class, account.getUserId());
//        account.setBalance(account.getBalance().subtract(transferAmount));
//        sql = "UPDATE account SET balance = ? WHERE user_id = ?";
//        try {
//            jdbcTemplate.update(sql, account.getUserId(), account.getBalance());
//        } catch (DataAccessException exception) {
//            return false;
//        }

    private Account mapRowToAccount(SqlRowSet rs){
        Account account = new Account();
        account.setBalance(rs.getBigDecimal("balance"));
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getLong("user_id"));
        return account;
    }
}



