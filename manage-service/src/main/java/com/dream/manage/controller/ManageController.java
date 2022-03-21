package com.dream.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dream.manage.dto.ManageDto;
import com.dream.manage.service.ManageService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@RequestMapping("/pms")
@Controller
@AllArgsConstructor
@Slf4j
public class ManageController {
	private ManageService service;
	//@RolesAllowed("ADMIN")
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute("dto") ManageDto dto) {
		service.register(dto);
		return "regist_success";
	}
	@PostMapping("/modify")
	public String modify() {
		return "asd";
	}
	@GetMapping("/delete")
	public String delete() {
		return "asd";
	}
}
