package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

}

//
//    private boolean createAccount (String username, String password) {
//
//        String sql = "INSERT INTO account (user_id, balance) "
//                + "VALUES (SELECT user_id FROM user WHERE username = ?, ?)";
//        jdbcTemplate.queryForObject(sql, username, )
//
//    }