package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

    @Override
    public Account getAccount(int id) {

        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if(rowSet.next()){
            return mapRowToAccount(rowSet);
        }
        throw new UsernameNotFoundException("Account Id not found.");
    }

    @Override
    public void updateBalance(Account account) {


    }
    private Account mapRowToAccount(SqlRowSet rs){
        Account account = new Account();
        account.setBalance(rs.getBigDecimal("balance"));
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getLong("user_id"));
        return account;

    }
}



