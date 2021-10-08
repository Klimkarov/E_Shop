package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
@Transactional
@Controller
public class CategoryController {
	
	@Autowired
	CategoryRepository categoryRepo;
	

	@GetMapping("/showNewCategory")
	public String showNewCategory(Model model) {
		Category category = new Category();
		model.addAttribute("category", category);
		return "new_category";
	}

	@PostMapping("/saveCategory")
	public String saveCategoryRepo(@ModelAttribute("category") Category category) {
		categoryRepo.save(category);
		return "redirect:/showNewProductForm";
	}
	
	
	@GetMapping("/showUpdateCategoryForm/{id}") 
	public String showUpdateForm(@PathVariable("id")Integer id, Model model) {
		Category category = categoryRepo.findById(id).get();
		model.addAttribute("category", category);
		return "update_category";
	}
	

	
	
	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable("id")Integer id) {
		categoryRepo.deleteById(id);
		return "redirect:/product";
	}

}
