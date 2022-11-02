package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/accounts/")
@RestController
public class AccountController {
    private AccountDao accountDao;

    public AccountController (AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @ApiOperation("getAccount")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Account listUsers(@ApiParam("Enter the id") @RequestParam Long id) {
        return accountDao.getAccount(id);
    }

}