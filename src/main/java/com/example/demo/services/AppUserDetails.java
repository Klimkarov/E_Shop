package com.example.demo.services;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Address;
import com.example.demo.entity.Product;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;


@SuppressWarnings("serial")
@Service
@Transactional
@Component
public class AppUserDetails implements UserDetails {

	@Autowired
	UserRepository userRepo;

	private User user;

	public AppUserDetails() {
		super();
	}

	public AppUserDetails(User user) {
		super();
		this.user = user;
	}
	
	
	public Object getUser() {
		
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

//	private User user;
//
//	public UsersDetails(User user) {
//		super();
//		this.user = user;
//	}
	

// method for role //
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = user.getRoles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));

		}
		return authorities;
	}
	
	public void save(User user, MultipartFile file) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.contains("..")) {
			System.out.println("not a valid file");
		}
		try {
			user.setImage(Base64.getEncoder().encodeToString(file.getBytes())); //u classa e string
		//	user.setImage(Base64.getEncoder().encode(file.getBytes())); u classa e byte[]
		} catch (IOException e) {

			e.printStackTrace();
		}

		userRepo.save(user);

	}
	
	
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}


	@Override
	public String getUsername() {
		return user.getUserName();
	}

	public String getEmail() {
		return user.getEmail();
	}


	public String getFirstName() {
		return user.getFirstName();
	}


	public String getLastName() {
		return user.getLastName();
	}



	public String getImage() {
		return user.getImage();
	}
	
	
	public String getCountry() {
		return user.getAddress().getCountry();
	}

	public String getCity() {
		return user.getAddress().getCity();
	}

	public String getStreet() {
		return user.getAddress().getStreet();
	}

	public Integer getZip() {
		return user.getAddress().getZip();
	}
	
	

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getFullName() {
		return user.getFirstName() + " " + user.getLastName();
	}

}
