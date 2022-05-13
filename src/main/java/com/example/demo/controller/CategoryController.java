package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;

@Transactional
@Controller
public class CategoryController {
	
	@Autowired
	CategoryRepository categoryRepo;
	
	@Autowired
	UserRepository userRepo;
	

	@GetMapping("/showNewCategory")
	public String showNewCategory(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		Category category = new Category();
		model.addAttribute("category", category);
		
		// for user profile image
		String userName = userDetails.getUsername();
	    User userImage = userRepo.findByUserName(userName);
		model.addAttribute("userImage", userImage);
		   
		return "new_category";
	}

	@PostMapping("/saveCategory")
	public String saveCategoryRepo(@ModelAttribute("category") Category category) {
		categoryRepo.save(category);
		return "redirect:/showNewProductForm";
	}
	
	
	@GetMapping("/showUpdateCategoryForm/{id}") 
	public String showUpdateForm(@PathVariable("id")Integer id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		Category category = categoryRepo.findById(id).get();
		model.addAttribute("category", category);
		
		        // for user profile image
				String userName = userDetails.getUsername();
			    User userImage = userRepo.findByUserName(userName);
				model.addAttribute("userImage", userImage);
		
		return "update_category";
	}
	

	
	
	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable("id")Integer id) {
		categoryRepo.deleteById(id);
		return "redirect:/product";
	}

}
