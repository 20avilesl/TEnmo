package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users/")
@RestController
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }
    @ApiOperation("lists all users")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> listUsers() {
        return userDao.findAll();
    }
    @ApiOperation("find user")
    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public User findUser(@ApiParam("find user by username") @RequestParam String username) {
        return userDao.findByUsername(username);
    }
//    @ApiOperation("find Id by username")
//    @RequestMapping(value = "accounts/{username}", method = RequestMethod.GET)
//    public int findId(@ApiParam("find Id of user") @RequestParam String username) {
//        return userDao.findIdByUsername(username);
//    }

}
