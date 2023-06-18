package com.example.authorizationdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/greet")
    public String greet(){
        return "Good Evening";
    }

    @GetMapping("/home")
    public String home(){
        return "Welcome To Home Page";
    }
}
