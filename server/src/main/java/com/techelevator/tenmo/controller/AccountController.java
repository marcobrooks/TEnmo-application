package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {

    private final TokenProvider tokenProvider;
    private AccountDao accountDao;

    public AccountController(TokenProvider tokenProvider, AccountDao accountDao) {
        this.tokenProvider = tokenProvider;
        this.accountDao = accountDao;
    }

    @RequestMapping(value= "/balance", method = RequestMethod.GET)
    public double getBalance(String username, Principal principal) {
        double accountDaoBalance = accountDao.getBalance(principal.getName());
        return accountDaoBalance;
    }


}
