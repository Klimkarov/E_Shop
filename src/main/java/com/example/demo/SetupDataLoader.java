package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Address;
import com.example.demo.entity.Category;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;



@Transactional
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	AddressRepository addressRepo;
	
	@Autowired
	CategoryRepository categoryRepo;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (!userRepo.existsByEmailIgnoreCase("admin@yahoo.com")) {

		//	createRoleIfNotFound("ROLE_ADMIN");
		//	createRoleIfNotFound("ROLE_USER");


			List<Category> category1 = new ArrayList<>();
			category1.add(new Category(1, "MAN"));
			category1.add(new Category(2, "WOMAN"));
			category1.add(new Category(3, "KIDS"));
			categoryRepo.saveAll(category1);
		
			
			Role adminRole = new Role("ROLE_ADMIN");
			roleRepo.save(adminRole);
			
			Role userRole = new Role("ROLE_USER");
			roleRepo.save(userRole);
			
			User user = new User();
			user.setFirstName("Sashko");
			user.setLastName("Klimkar");
			user.setUserName("Admin_Test");
			user.setPassword(new BCryptPasswordEncoder().encode("adminpass"));
			user.setEmail("admin@yahoo.com");
			
			Address address = new Address();
			address.setCountry("Macedonia");
			address.setCity("Veles");
			address.setStreet("Str.Program");
			address.setZip(1400);
			
			user.setAddress(address);
			
	//		user.setRoles(Arrays.asList(adminRole));
			user.setRoles(Collections.singleton(adminRole));

	
			LocalDate date =  LocalDate.now();
			user.setCreated(date);

			userRepo.save(user);
		}	
	}
		
	
		Role createRoleIfNotFound(String name) {

			Role role = roleRepo.findByName(name);
			if (role == null) {
				role = new Role(name);
				roleRepo.save(role);
			}
			return role;
		}
		

}
