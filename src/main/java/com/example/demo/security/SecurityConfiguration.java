package com.example.demo.security;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.services.AppUserDetailsService;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{
	
	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			// TODO Auto-generated method stub
			//WebMvcConfigurer.super.addResourceHandlers(registry);
		registry
		.addResourceHandler("/URLToReachResourcesFolder/**")
		.addResourceLocations("/resources");
		}
		
	
	
//	    @Autowired
//	    private DataSource dataSource;
	     
	    @Bean
	    public UserDetailsService userDetailsService() {
	        return new AppUserDetailsService();
	    }
	     
	    @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	     
	    @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userDetailsService());
	        authProvider.setPasswordEncoder(passwordEncoder());
	         
	        return authProvider;
	    }
	 
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.authenticationProvider(authenticationProvider());
	    }
	    
//	    after login, next is this method 
	    
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.authorizeRequests() 
	                                
	            .antMatchers("/index", "/header/**", "/shoppingCart/**", "/showOrder/**", "/showOrderHistory/**", "/showUserProfile/**","/addProduct/{proId}/**").authenticated()
	            .antMatchers("/images/**", "/css/**", "/js/**", "/webjars/**").permitAll()
	            
	            .antMatchers("/deleteProduct/**").hasAuthority("ROLE_ADMIN")
	            .antMatchers("/showUpdateProductForm/**").hasAuthority("ROLE_ADMIN")
	            .antMatchers("/showUsers/**").hasAuthority("ROLE_ADMIN")
	            .anyRequest().permitAll()
	            
	            .and()
	            .formLogin().loginPage("/login").permitAll()
	                
	            .and()
	            .logout()
	            .invalidateHttpSession(true)
	            .clearAuthentication(true)
	            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	            .logoutSuccessUrl("/login?logout").permitAll()
	            
	            .and()
	            .exceptionHandling().accessDeniedPage("/403");
	        
	    }
	

}
