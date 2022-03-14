package com.dream.userservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String home() {
		System.out.println("controller stasrt");
		
		return "index";
	}
	
	@GetMapping("/loanList")
	public String loanList() {
		return "loanList";
	}
	
	@GetMapping("/startup")
	public String startup() {
		return "startup";
	}
	
	@GetMapping("/info")
    public String info(@Value("${server.port}") String port) {
        return "User 서비스의 기본 동작 Port: {" + port + "}";
    }
	
	@GetMapping("/auth")
    public String auth(@RequestHeader(value = "token") String token) {
        return "token is " + token;
    }
}
