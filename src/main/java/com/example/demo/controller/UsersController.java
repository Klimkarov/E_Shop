package com.example.demo.controller;

import java.io.IOException;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Address;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Role;
import com.example.demo.entity.RoleName;
import com.example.demo.entity.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.AppUserDetails;
import com.example.demo.services.UserService;

@Transactional
@Controller
public class UsersController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	AddressRepository addressRepo;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	AppUserDetails appUserDetails;

	@Autowired
	UserService userService;



	@GetMapping("/showRegistrationUser")
	public String showRegistrationForm(Model model) {

		model.addAttribute("user", new User());

		return "registration_User";
	}


	@PostMapping("/saveUserRegistration")
	public String processRegister(@ModelAttribute("user") @Valid User user, 
			RedirectAttributes redirAttrs, MultipartFile file, Address address, BindingResult result)throws IOException {
		
	//	existing is equal of already stored emails
		User existing = userRepo.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            return "showRegistrationUser";
        }
        

		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());

		LocalDate date = LocalDate.now();
		
//		user.setImage(file.getBytes());
		user.setCreated(date);
		user.setEnabled(true);
		user.setPassword(encodedPassword);

		Role role = new Role("ROLE_USER");
		roleRepo.save(role);
		user.setRoles((Collections.singleton(role)));
		userService.save(user, file);
		
		return "redirect:/showRegistrationUser?success";
	}

	
	
	@GetMapping("/showUserProfile")
	public String showUserProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
	
		String userName = userDetails.getUsername();
		User user = userRepo.findByUserName(userName);

		model.addAttribute("user", user);

		List<User> listUser = userRepo.findAll();
		model.addAttribute("listUser", listUser);

		List<String> listProfessional = new ArrayList<>();
		listProfessional = Arrays.asList("Java Developer", ".Net Developer", 
				       "Tester", "WebDesigner", "Marketing Manager", "Arts");
		
		model.addAttribute("listProfessional", listProfessional);

		return "viewProfile";

	}
	

	@PostMapping("/updateUserProfile")
	public String saveUserProfile(@ModelAttribute("user") User user, @AuthenticationPrincipal UserDetails userDetails,
			Address address, Model model, MultipartFile file) throws IOException {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());

		LocalDate date = LocalDate.now();

		Role role = new Role("ROLE_USER");
		roleRepo.save(role);
		user.setRoles((Collections.singleton(role)));
		user.setCreated(date);

//		user.setImage(file.getBytes());
		user.setEnabled(true);
		user.setPassword(encodedPassword);
		user.setUserName(user.getUserName());
		userService.save(user, file);
		userRepo.save(user);

		return "viewProfile";
	}

	
	@GetMapping("/showUsers")
	public String showUsers(Model model, @Param("keyword") String keyword, Role role,
			@AuthenticationPrincipal UserDetails userDetails) {

		String userName = userDetails.getUsername();
		User user = userRepo.findByUserName(userName);
		model.addAttribute("user", user);

		model.addAttribute("user", new User());

		List<User> listUsers = userService.listAll(keyword);
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("keyword", keyword);


		List<Role> roleOptions = new ArrayList<>();
		roleOptions.add(new Role(1, "ROLE_ADMIN"));
		roleOptions.add(new Role(2, "ROLE_USER"));

		model.addAttribute("listRoles", roleOptions);

		return "users";
	}

	@GetMapping("/showUpdateUser/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model, MultipartFile file,
			@AuthenticationPrincipal UserDetails userDetails) {

		User user = userRepo.findById(id).get();
		model.addAttribute("user", user);

		List<Role> roleOptions = new ArrayList<>();
		roleOptions.add(new Role(1, "ROLE_ADMIN"));
		roleOptions.add(new Role(2, "ROLE_USER"));
		model.addAttribute("roleOptions", roleOptions);

		return "update_user";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") Integer id) {
		userRepo.deleteById(id);
		return "redirect:/showUsers";
	}

	@PostMapping("/addUser")
	public String addUserr(@ModelAttribute("user") User user, Model model, Address address, Role role,
			MultipartFile file) {



		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());

		LocalDate date = LocalDate.now();
		user.setCreated(date);
		user.setPassword(encodedPassword);

		userService.save(user, file);

		return "redirect:/showUsers";
	}

	
	@PostMapping("/saveUpadateUser")
	public String saveUpdateUser(@ModelAttribute("user") User user, Model model, Address address, Role role,
			MultipartFile file) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());

		LocalDate date = LocalDate.now();
		user.setCreated(date);

		user.setPassword(encodedPassword);


		userService.save(user, file);

		return "redirect:/showUsers";
	}



}
