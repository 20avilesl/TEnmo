package com.techelevator.tenmo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping("/api/test/")
@RestController
public class TestController {

    @RequestMapping(method = RequestMethod.GET)
    public String testMethod(){
        return 'hello world'
    }
}