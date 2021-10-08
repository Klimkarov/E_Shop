package com.example.demo.controller;

import java.util.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;

@Controller
public class ViewController {
	
	
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	CategoryRepository categoryRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/index")
	public String viewHomePage(Model model, @Param("keyword") String keyword, @AuthenticationPrincipal UserDetails userDetails) {
		
		List<User> listUsers = userService.listAll(keyword);
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("keyword", keyword);
			
		return "index";
	}
	
// Start App with this URL // 	
	@GetMapping("/")
    public String root(Model model) {
		
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);
		
        return "index";
    }
	
// Method for loging //
	@GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }
	
// Display Page for Error //	
	@GetMapping("/403")
	public String error403(@AuthenticationPrincipal UserDetails userDetails) {
		
		String userName = userDetails.getUsername();
	
		User user = userRepo.findByUserName(userName);
		
		return "403_error";
	}


}
