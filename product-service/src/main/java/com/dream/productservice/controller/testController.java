package com.dream.productservice.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dream.productservice.dto.ProductDto;
import com.dream.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
public class testController {
	@GetMapping("/hello")
	public String getHello() {
		return "Hello, this is test Project!";
	}
}
