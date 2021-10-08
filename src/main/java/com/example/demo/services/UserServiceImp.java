//package com.example.demo.services;
//
//import java.io.IOException;
//
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Base64;
//import java.util.Collection;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import javax.transaction.Transactional;
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//
//import com.example.demo.entity.Role;
//import com.example.demo.entity.User;
//
//import com.example.demo.repository.RoleRepository;
//import com.example.demo.repository.UserRepository;
//
//@Service
//@Transactional
//public class UserServiceImp {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	RoleRepository roleRepository;
//
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
//
//   
//
////	@Override
////	public User findByEmail(String email) {
////		return (User) userRepository.findByEmail(email);
////	}
////
//////	@Override
//////	public User save(User userDto) {
//////		
//////		LocalDate date = LocalDate.now();
//////    	User user = new User(userDto.getFirstName(),userDto.getLastName(),
//////		userDto.getUserName(),userDto.getEmail(),userDto.getDate(),
//////		passwordEncoder.encode(userDto.getPassword()), 
////		Arrays.asList(new Role("ROLE_USER")));
//////    	user.setDate(date);
//////        return userRepository.save(user);
//////	}
////	
//////	@Autowired(required=true)
//////	private User user;
////	
////   public UserServiceImp() {
////		
////	}
////	
////	private User user;
////	
////	public UserServiceImp(User user) {
////		 this.user=user;
////	}
////
////	public User save(@Valid UserRegistrationDto registration) {
////
////		User user = new User();
////		LocalDate date = LocalDate.now();
////
////		user.setFirstName(registration.getFirstName());
////		user.setLastName(registration.getLastName());
////		user.setEmail(registration.getEmail());
////		user.setUserName(registration.getUserName());
////		user.setImage(registration.getImage());
////		user.setDate(date);
////      
////		user.setPassword(passwordEncoder.encode(registration.getPassword()));
////		user.setRoles(Arrays.asList(new Role("ROLE_USER")));
////		
////
////		// user.setRoles(Arrays.asList(new Role("ROLE_ADMIN")));
////
////		return userRepository.save(user);
////	}
////	
////
////// LOGIN METOD //
////	
////	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//		// List<User> user1 = userRepository.findByUserName(userName);
//		// UserRegistrationDto registration = new UserRegistrationDto();
//
////		User user = (User) userRepository.findByEmail(email);
////		if (user == null) {
////			throw new UsernameNotFoundException("Invalid E-mail or password.");
////		}
////		
////        if(user.getEmail()!= null) {
////       	 user.setSignIn(true);
////        }
////        
////        userRepository.save(user);
////
////   return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
////				mapRolesToAuthorities(user.getRoles()));
////	}
////	
////	// metoda za da go zeme imeto na koj tip rolja e userot //
////	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
////		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
////	}
////	
////	
////	// ist metod //
//////	private Collection<? extends GrantedAuthority> getAuthorities(){
//////		Set<Role> roles = user.getRoles();
//////		List<SimpleGrantedAuthority> authorithies = new ArrayList<>();
//////		
//////		for (Role role : roles) {
//////			authorithies.add(new SimpleGrantedAuthority(role.getName()));
//////			
//////		}
//////		return authorithies;
//////	}
////	
//////    @Override
//////    public UserDetails loadUserByUserName(String username) throws UsernameNotFoundException {
//////        User user = userRepository.findByUserName(username);
//////        if (user == null){
//////            throw new UsernameNotFoundException("Invalid username or password.");
//////        }
//////        return new org.springframework.security.core.userdetails.User(user.getUserName(),
//////                user.getPassword(),
//////                mapRolesToAuthorities(user.getRoles()));
//////    }
////
////	
////
////	public void save(User user, MultipartFile file) {
////
////		String fileName = StringUtils.cleanPath(file.getOriginalFilename()); // pateka do image //
////		if (fileName.contains("..")) {
////			System.out.println("not a valid file");
////		}
////		try {
////			user.setImage(file.getBytes());
////			// user.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
////		} catch (IOException e) {
////
////			e.printStackTrace();
////		}
////
////		userRepository.save(user);
////	}
////
////	@Override
////	public Collection<? extends GrantedAuthority> getAuthorities() {
////		// TODO Auto-generated method stub
////		return null;
////	}
////
////	@Override
////	public String getPassword() {
////		// TODO Auto-generated method stub
////		return null;
////	}
////
////	@Override
////	public String getUsername() {
////		// TODO Auto-generated method stub
////		return null;
////	}
////	
//////	public byte[] getImage(User user) {
//////		return user.getImage();
//////	}
////	
////	public String getfirstName() {
////		// TODO Auto-generated method stub
////		return user.getFirstName();
////	}
////	
//////	public User getUser() {
//////		return this.user;
//////	}
////
////	@Override
////	public boolean isAccountNonExpired() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean isAccountNonLocked() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean isCredentialsNonExpired() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	@Override
////	public boolean isEnabled() {
////		// TODO Auto-generated method stub
////		return false;
////	}
////
////	
////
////	
////
////	
////
////}
