package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

    @Override
    public Account getAccount(Long id) {

        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if(rowSet.next()){
            return mapRowToAccount(rowSet);
        }
        throw new UsernameNotFoundException("User ID not found.");
    }

    @Override
    public void updateBalance(Account account) {
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

    }
    private Account mapRowToAccount(SqlRowSet rs){
        Account account = new Account();
        account.setBalance(rs.getBigDecimal("balance"));
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getLong("user_id"));
        return account;

    }
}



