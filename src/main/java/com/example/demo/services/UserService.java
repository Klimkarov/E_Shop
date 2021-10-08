package com.example.demo.services;

import java.io.IOException;

import java.util.Base64;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.User;

import com.example.demo.repository.UserRepository;

// UserDetailsService is made interface from SpringBoot with method
// UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepository userRepo;

	public List<User> listAll(String keyword) {
		if (keyword != null) {
			return userRepo.findAll(keyword);
		}
		return userRepo.findAll();
	}
	
	// Metod for Image - get and put in Base//
		public void save(User user, MultipartFile file) {

			String fileName = StringUtils.cleanPath(file.getOriginalFilename()); // pateka do image //
			if (fileName.contains("..")) {
				System.out.println("not a valid file");
			}
			try {
				//                           encodeToString because parametar for image is string    
				user.setImage(Base64.getEncoder().encodeToString(file.getBytes())); 
			} catch (IOException e) {

				e.printStackTrace();
			}

			userRepo.save(user);
		}


//	public User getCurrentlyLoggedInUser(Authentication authentication) {
//		
//		if(authentication == null) return null;
//		
//		User user = null;
//		Object principal = authentication.getPrincipal();
//		
//		if(principal instanceof AppUserDetails) {
//			user = ((AppUserDetails) principal).getUser();
//		}else if{
//		
//		
//		
//		
//		return user;
//		
//	}
	
}
