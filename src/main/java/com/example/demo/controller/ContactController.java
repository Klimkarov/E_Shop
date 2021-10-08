package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.AppUserDetails;


@Controller
public class ContactController {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	private JavaMailSender mailSender;

	
	@GetMapping("/aboutUs")
	public String contact(Model model, @AuthenticationPrincipal UserDetails userDetails) {
	
		return "aboutUs_Page";
	}
	
	
	@PostMapping("/aboutUs")
	public String submitContact(HttpServletRequest request, @RequestParam ("attachment") MultipartFile file) throws MessagingException, IOException {
		String fullName = request.getParameter("fullName");
		String email = request.getParameter("email");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");


		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		String mailSubject = fullName + " has sent a message";
		String mailContent = "<p><b>Sender Name:</b> " + fullName + "</p>";
		mailContent += "<p><b>Email: " + email + "</p>";
		mailContent += "<p><b>Subject: " + subject + "</p>";
		mailContent += "<p><b>Content: " + content + "</p>";

		mailContent += "<hr><img align='center' src='cid:logoImage'>"; 
		
		helper.setFrom("julijor22@gmail.com", "Adidas");
		helper.setTo("sashkoklimkarov9@gmail.com");
		helper.setSubject(mailSubject);
		helper.setText(mailContent, true);
		ClassPathResource resource = new ClassPathResource("/static/images/adidas.png");
		helper.addInline("logoImage", resource);
		
		if(!file.isEmpty()) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			
			InputStreamSource source = new InputStreamSource() {

				@Override
				public InputStream getInputStream() throws IOException {
					// TODO Auto-generated method stub
					return file.getInputStream();
				}							
			};

			helper.addAttachment(fileName, source);
		}
		
		mailSender.send(message);

		return "message";
	}
	
	
	
}
