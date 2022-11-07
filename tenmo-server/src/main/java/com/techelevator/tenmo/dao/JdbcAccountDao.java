package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
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

    @Override
    public void updateBalance(Account account) {
        System.out.println(account.toString());
        String sql = "UPDATE account SET balance = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, account.getBalance(), account.getUserId());
    }

    @Override
    public BigDecimal getBalance(String username) {

        String sql = "SELECT balance FROM account as a JOIN tenmo_user as u ON u.user_id = a.user_id WHERE u.username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BigDecimal.class, username);
        } catch (DataAccessException exception) {
            return null;
        }
    }

    @Override
    public BigDecimal getBalance(int id) {
        String sql = "SELECT balance FROM account WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BigDecimal.class, id);
        } catch (DataAccessException exception) {
            return null;
        }
    }

    private Account mapRowToAccount(SqlRowSet rs){
        Account account = new Account();
        account.setBalance(rs.getBigDecimal("balance"));
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getLong("user_id"));
        return account;
    }
}



