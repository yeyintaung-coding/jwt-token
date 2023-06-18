package com.example.githuboauthdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    private Logger logger= LoggerFactory.getLogger(MainController.class.getName());

    @RequestMapping("/")
    public String main(){
//        logger.info(String.valueOf(token));
        return "main";
    }
}
