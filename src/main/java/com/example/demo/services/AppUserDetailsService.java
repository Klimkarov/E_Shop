package com.example.demo.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Collection;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class AppUserDetailsService implements UserDetailsService {
	
	
	@Autowired
    private UserRepository userRepo;
     
   
	@Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(userName);
    
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
//        }
//        return new AppUserDetails(user);
    }
       
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities((Set<Role>) user.getRoles()));
        
        
	}
	
	//get name of the role //
	private List<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
		return roles.stream().map
				(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		
    }

}
