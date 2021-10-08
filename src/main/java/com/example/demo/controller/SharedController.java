//package com.example.demo.controller;
//
//import java.security.Principal;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.example.demo.entity.User;
//import com.example.demo.repository.UserRepository;
//
//
//@Component
//@ControllerAdvice // So ovoj tip na controler mozam da imam pristap do sekoj element na userot //
//public class SharedController {
//	
//	@Autowired
//	UserRepository userRepo;
//	
////	@Autowired
////	UserServiceImp userServiceImp;
//	
//	@ModelAttribute()
//	public void SharedAttrs(User user, HttpServletRequest request, Model model, Principal principal, MultipartFile file) {
//		try {
//			
//		//	List<User>user = userRepo.findAll();
////			String base64EncodedImage = userServiceImp.getImage();
////			String username = principal.getName();
////			User user = userRepository.findByUserName(username);
//			model.addAttribute("loggedUser", user);
//		//	model.addAttribute("base64EncodedImage", base64EncodedImage);
//		
//		}
//		catch(Exception e) {
//			model.addAttribute("loggedUser", null);
//			
//		}
//		
//		
//	}
//
//
//}