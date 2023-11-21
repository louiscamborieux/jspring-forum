package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/bonjour")
	public String index() {
		return "Bonjour tout le monde !";
	}

}