//package com.example.demo.entity;
//
//import java.time.LocalDate;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.persistence.Lob;
//import javax.validation.constraints.AssertTrue;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotEmpty;
//
//import com.example.demo.fieldmatch.FieldMatch;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//
//@FieldMatch.List({
//    @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
//    @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
//})
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Transactional
//public class UserRegistrationDto {
//	
//	@NotEmpty
//    private String firstName;
//
//    @NotEmpty
//    private String lastName;
//    
//    @NotEmpty
//    private String userName;
//    @NotEmpty
//    private String confirmUserName;
//
//    @NotEmpty
//    private String password;
//    @NotEmpty
//    private String confirmPassword;
//
//    @Email
//    @NotEmpty
//    private String email;
//    @Email
//    @NotEmpty
//    private String confirmEmail;
//    
//    @Lob
//    private byte[] image;
//    
//    private LocalDate date;
//    
//    @AssertTrue
//    private Boolean terms;
//
//	
//
//
//
//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getConfirmPassword() {
//		return confirmPassword;
//	}
//
//	public void setConfirmPassword(String confirmPassword) {
//		this.confirmPassword = confirmPassword;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getConfirmEmail() {
//		return confirmEmail;
//	}
//
//	public void setConfirmEmail(String confirmEmail) {
//		this.confirmEmail = confirmEmail;
//	}
//
//	public byte[] getImage() {
//		return image;
//	}
//
//	public void setImage(byte[] image) {
//		this.image = image;
//	}
//
//	public LocalDate getDate() {
//		return date;
//	}
//
//	public void setDate(LocalDate date) {
//		this.date = date;
//	}
//
//	public Boolean getTerms() {
//		return terms;
//	}
//
//	public void setTerms(Boolean terms) {
//		this.terms = terms;
//	}
//   
//    
//
//}
