package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@SpringBootApplication
public class DemoApplication {
	
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	

}
