package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.exceptions.InsufficientFundsException;
import com.techelevator.tenmo.exceptions.InvalidReceiverException;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
@PreAuthorize("isAuthenticated()")
@RestController
public class TransactionController {


    private final TokenProvider tokenProvider;
    private TransactionDao transactionDao;
    private UserDao userDao;
    private AccountDao accountDao;

    public TransactionController(TokenProvider tokenProvider, TransactionDao transactionDao, UserDao userDao, AccountDao accountDao) {
        this.tokenProvider = tokenProvider;
        this.transactionDao = transactionDao;
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(value = "/payable-user", method = RequestMethod.GET)
    public List<String> payableUsers() {
            return userDao.payableUser();
        }



    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public void transfer (@Valid @RequestBody Transaction body, Principal principal) throws InsufficientFundsException, InvalidReceiverException {
        int senderId = getAccountIdByUsername(principal.getName());
        int receiverId = body.getReceiverId();
            if (receiverId == senderId){
                throw new InvalidReceiverException();
            }
        double amountTransferred = body.getAmountTransferred();
        accountDao.subtractBalance(senderId, amountTransferred);
        accountDao.addBalance(receiverId, amountTransferred);
        transactionDao.transfer(amountTransferred, senderId, receiverId);

    }

    @RequestMapping(value = "/get-transactions", method = RequestMethod.GET)
    public List<Transaction> transactionList(Principal principal) {
        String user = principal.getName();
        return transactionDao.transactionList(user);
    }

    @RequestMapping(value = "/find-transaction/{transaction_id}", method = RequestMethod.GET)
    public Transaction findTransactionById(@PathVariable Integer transaction_id) {
    return transactionDao.findTransactionById(transaction_id);
    }

    private int getAccountIdByUsername(String username){
        return accountDao.getAccountIdByUserId( userDao.findIdByUsername(username));
    }

    private String getUsernameByAccountId(int accountId){
        return accountDao.getUsernameByAccountId(accountId);
    }


}
