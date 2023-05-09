package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.InsufficientFundsException;
import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    boolean createAccount(int userId);

    List<Account> listAccounts(int userId);

    double getBalance(String username);

    void addBalance(int accountId, double amountTransferred);

    void subtractBalance(int accountId, double amountTransferred) throws InsufficientFundsException;

    int getAccountIdByUserId(int userId);

    String getUsernameByAccountId(int accountId);

}
