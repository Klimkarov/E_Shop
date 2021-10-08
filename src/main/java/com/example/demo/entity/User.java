package com.example.demo.entity;

import java.io.Serializable;

import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Transient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Transient
@Transactional
public class User implements Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 20)
	private String firstName;
	
	@Column(nullable = false, length = 20)
	private String lastName;
	
	@Column(nullable = false, unique = true, length = 20)
	private String userName;
	
	@Column(nullable = false, unique = true, length = 64)
	private String password;
	
	@Email
	@NaturalId
	@Column(name="Email", nullable = false, unique = true, length = 45)
	private String email;
	
	private String profession;
	
	private Boolean employed;
	
	@Column(name="Birthday", unique = true, length = 10)
	@DateTimeFormat(pattern="dd/MM/yyyy") //  ako ima / togas i na front pri vnes ke bara so / 
	private Date birthday;
	
	private String gender;
	
	@Column(name="Enabled")
	private Boolean enabled;	
	
	
	@Column(name="Date")
	private LocalDate created;
	
	
//	@Lob
//	@Basic(fetch = FetchType.LAZY)
//	private byte[] image; 
	
	@Lob
	private String image;
	
	@AssertTrue
    private Boolean terms;
	
	@ManyToOne(cascade = CascadeType.ALL)// bez cascada vo user registration ne snima
	private Address address;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Product product;
	
	@OneToMany(mappedBy = "user")
	private List<ShoppingCart> shoppingCart;
	
//	@ManyToOne(cascade = CascadeType.ALL)
//	private Payment payment;
	
	@OneToMany(mappedBy = "user")
	private List<OrderHistory> orderHistory;
	
	@OneToMany(mappedBy = "user")
	private List<Payment> payment;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles" , 
	           joinColumns = @JoinColumn(name = "user_id"), 
	           inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>() ;
	

	public void addRole(Role role) {
		this.roles.add(role);
	}


	public User(String firstName, String lastName, String userName, String password, String email, String profession,
			Boolean employed, Date birthday, String gender, Boolean enabled, LocalDate created, String image,
			@AssertTrue Boolean terms, Address address, Product product, List<ShoppingCart> shoppingCart,
			List<Payment> payment, List<OrderHistory> orderHistory, Set<Role> roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.profession = profession;
		this.employed = employed;
		this.birthday = birthday;
		this.gender = gender;
		this.enabled = enabled;
		this.created = created;
		this.image = image;
		this.terms = terms;
		this.address = address;
		this.product = product;
		this.shoppingCart = shoppingCart;
		this.payment = payment;
		this.orderHistory = orderHistory;
		this.roles = roles;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName
				+ ", password=" + password + ", email=" + email + ", profession=" + profession + ", employed="
				+ employed + ", birthday=" + birthday + ", gender=" + gender + ", enabled=" + enabled + ", created="
				+ created + ", image=" + image + ", terms=" + terms + ", address=" + address + ", product=" + product
				+ ", shoppingCart=" + shoppingCart + ", payment=" + payment + ", orderHistory=" + orderHistory
				+ ", roles=" + roles + "]";
	}

	
}