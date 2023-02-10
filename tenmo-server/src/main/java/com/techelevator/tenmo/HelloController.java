package com.techelevator.tenmo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    private String [] getEmployees() {
        String [] employees = {"bob", "jeff", "sam", "raymundo", "steve"};
        return employees;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String [] hello() {
        return getEmployees();
    }
}
