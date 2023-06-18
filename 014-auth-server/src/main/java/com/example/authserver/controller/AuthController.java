package com.example.authserver.controller;

import com.example.authserver.ds.Otp;
import com.example.authserver.ds.User;
import com.example.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/user/add")
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @PostMapping("/user/auth")
    public void auth(@RequestBody User user){
        userService.auth(user);
    }

    @PostMapping("/otp/check")
    public void check(@RequestBody Otp otp, HttpServletResponse response){
        if(userService.check(otp)){
            response.setStatus(HttpServletResponse.SC_OK);
        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
//curl -v -XPOST -H "content-type:application/json" -d '{"username":"john","password":"12345"}' localhost:8080/user/add
//curl -v -XPOST -H "content-type:application/json" -d '{"username":"john","password":"12345"}' localhost:8080/user/auth
//curl -v -XPOST -H "content-type:application/json" -d '{"username":"john","code":"4254"}' localhost:8080/otp/check