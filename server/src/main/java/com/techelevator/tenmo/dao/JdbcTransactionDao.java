package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransactionDao implements TransactionDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void transfer(double amountTransferred, int senderId, int receiverId) {

        String sql = "INSERT INTO transaction (sender_id, receiver_id, amount_transferred, status)" +
                " VALUES (?, ?, ?, 'Approved') RETURNING transaction_id";
        int newTransactionId;
        try {
             newTransactionId = jdbcTemplate.queryForObject(sql, Integer.class, senderId, receiverId, amountTransferred);
        } catch (EmptyResultDataAccessException e){
            System.out.println("Error transaction not found");
        }

    }

    @Override
    public List<Transaction> transactionList(String username) {
        List<Transaction> transactionList = new ArrayList<>();

        String sql = "SELECT transaction_id, sender_id, receiver_id, amount_transferred, status" +
                " FROM transaction t JOIN account a ON t.sender_id = a.account_id" +
        " JOIN tenmo_user u ON a.user_id = u.user_id WHERE username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while (results.next()) {
            transactionList.add(mapRowToTransaction(results));
        }
        return transactionList;
    }

    @Override
    public Transaction findTransactionById(Integer transaction_id)  throws EmptyResultDataAccessException {

        String sql = "SELECT transaction_id, sender_id, receiver_id, amount_transferred, status FROM transaction WHERE transaction_id = ?";
       SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transaction_id);
         if (rowSet.next()) {
          return  mapRowToTransaction(rowSet);
         }
        throw new EmptyResultDataAccessException(1);
    }

    private Transaction mapRowToTransaction (SqlRowSet rs) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(rs.getInt("transaction_id"));
        transaction.setSenderId(rs.getInt("sender_id"));
        transaction.setReceiverId(rs.getInt("receiver_id"));
        transaction.setAmountTransferred(rs.getDouble("amount_transferred"));
        transaction.setStatus(rs.getString("status"));

        return transaction;
    }

}

