package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.InsufficientFundsException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public boolean createAccount(int userId) {

        String sql = "INSERT INTO account (user_id, balance) " +
                "VALUES (?, 1000.00) RETURNING account_id";
        Integer newAccountId;

            jdbcTemplate.queryForObject(sql, Integer.class, userId);

        return true;
    }

    @Override
    public List<Account> listAccounts(int userId) {
        return null;
    }

    @Override
    public double getBalance(String username) {
        String sql = "SELECT balance FROM account a JOIN tenmo_user t ON a.user_id = t.user_id WHERE username = ?;";
        return jdbcTemplate.queryForObject(sql,Double.class, username);

    }

    @Override
    public void subtractBalance(int accountId, double amountTransferred) throws InsufficientFundsException {

            if (amountTransferred > getBalance(getUsernameByAccountId(accountId))) {
                amountTransferred = 0;
                throw new InsufficientFundsException();

            }

      String sql = "UPDATE account SET balance = (balance - ?) WHERE account_id = ?";
      jdbcTemplate.update(sql, amountTransferred, accountId);

    }

    @Override
    public void addBalance(int accountId, double amountTransferred){

        //if(accountId ==  )

        String sql = "UPDATE account SET balance = (balance + ?) WHERE account_id = ?";
       jdbcTemplate.update(sql, amountTransferred, accountId);

    }

    @Override
    public int getAccountIdByUserId(int userId){
        int accountId;
        String sql = "SELECT account_id FROM account WHERE user_id = ?";
        accountId = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return accountId;
    }

    @Override
    public String getUsernameByAccountId(int accountId){
        String sql = "SELECT username FROM tenmo_user t " +
                "JOIN account a ON a.user_id = t.user_id " +
                "WHERE account_id = ?";

      String username = jdbcTemplate.queryForObject(sql, String.class, accountId);

      return username;
    }


    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getDouble("balance"));
        return account;
    }
}
