package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final TokenProvider tokenProvider;
    private UserDao userDao;

    public UserController(TokenProvider tokenProvider, UserDao userDao) {
        this.tokenProvider = tokenProvider;
        this.userDao = userDao;
    }





}
