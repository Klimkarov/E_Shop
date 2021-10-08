package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.PathParam;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.MyOrder;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Product;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.entity.User;

import com.example.demo.repository.MyOrderRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShoppingCartRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.ShoppingCartService;

@Transactional
@Controller
public class MyOrderController {
	
	@Autowired
	PaymentRepository payRepo;

	@Autowired
	MyOrderRepository orderRepo;

	@Autowired
	ShoppingCartRepository shoppingCartRepo;

	@Autowired
	ShoppingCartService shoppingCartService;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ProductRepository productRepo;

		
	@GetMapping("/showOrder")
	public String showOrder( ShoppingCart shoppingCart, MyOrder order, Product product1, Model model,
	 @AuthenticationPrincipal UserDetails userDetails, BindingResult result) {

		String userName = userDetails.getUsername();
		User user = userRepo.findByUserName(userName);
		model.addAttribute("user", user);
		
		List<User> userList = userRepo.findAll();
		model.addAttribute("userList", userList);

		ShoppingCart cart = new ShoppingCart();
		model.addAttribute("cart", cart);

		List<MyOrder> listTotal = orderRepo.findAll();
		Double sum = listTotal.stream().map(x -> x.getSum()).reduce((double) 0, Double::sum);
		Double total = sum;
		
		model.addAttribute("total", total);
		model.addAttribute("firstName", user.getFirstName());

		List<User> user2 = userRepo.findAll();
		model.addAttribute("userList", user2);

		List<MyOrder> orderList = orderRepo.findAll();
		model.addAttribute("orderList", orderList);

		List<Product> productList = (List<Product>) productRepo.findAll();
		model.addAttribute("productList", productList);
		
		Payment payment = new Payment();
		model.addAttribute("payment", payment);
		
		List<ShoppingCart> listShoppingCart = shoppingCartRepo.findAll();
		model.addAttribute("listShoppingCart", listShoppingCart);      

		return "order";
	}
	
	
	@GetMapping("/deleteOrderById/{id}")
	public String deleteOrderById(@PathVariable("id") Integer id) {		
		orderRepo.deleteById(id);
		return "redirect:/showOrder";
	}
	


}