package com.example.demo.controller;

import java.util.Date;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

	@Autowired
	User user;

//	@GetMapping("/index")
//	public String viewHomePage(Model model, @Param("keyword") String keyword, @AuthenticationPrincipal UserDetails userDetails) {
//		
//		List<User> listUsers = userService.listAll(keyword);
//		model.addAttribute("listUsers", listUsers);
//		model.addAttribute("keyword", keyword);
//		
//		
//		return "index";
//	}

// Start App with this URL // 	
	@GetMapping("/")
	public String root(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		// for user profile image in nav bar
		// 1. if the user is not sign in, can go to home page
		// 2. and if make sign in and put image, the profile image we can see on many bar
//		List<User> listImage = userRepo.findAll();		
//		for (User userImage : listImage) {	
//			
//			   String userName = userDetails.getUsername();
//		       User user = userRepo.findByUserName(userName);
//			
//			if (userImage.getImage() != null || user == null) {          
//				userRepo.save(userImage);				
//				model.addAttribute("userImage", new User());	
//			}else
//			
//			if (userImage.getImage() != null || user != null) {          
//				userRepo.save(userImage);				
//				model.addAttribute("userImage", userImage);	
//			}
//		}
		
		

		return "index";
	}

// Method for loging //
	@GetMapping("/login")
	public String loginPage(Model model) {

		return "login";
	}

// Display Page for Error //	
	@GetMapping("/403")
	public String error403(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		// for user profile image in nav bar
		List<User> listImage = userRepo.findAll();
		for (User user : listImage) {
			if (user.getImage() != null) {

				String userName = userDetails.getUsername();
				User userImage = userRepo.findByUserName(userName);
				model.addAttribute("userImage", userImage);
			}
		}

		return "403_error";
	}

}
