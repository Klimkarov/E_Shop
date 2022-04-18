package com.example.demo.controller;

import java.time.LocalDate;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.ShoppingCart;
import com.example.demo.entity.User;
import com.example.demo.entity.Address;
import com.example.demo.entity.Category;
import com.example.demo.entity.MyOrder;
import com.example.demo.entity.OrderHistory;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Product;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.OrderHistoryRepository;
import com.example.demo.repository.MyOrderRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShoppingCartRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.ProductService;
import com.example.demo.services.ShoppingCartService;

@Controller
@Transactional

public class ShoppingCartController {

	@Autowired
	ProductRepository productRepo;

	@Autowired
	ProductService productService;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ShoppingCartService shoppingCartService;

	@Autowired
	ShoppingCartRepository shoppingCartRepo;

	@Autowired
	AddressRepository addressRepo;

	@Autowired
	MyOrderRepository orderRepo;
	
	@Autowired
	PaymentRepository payRepo;
	
	@Autowired
	OrderHistoryRepository orderHistRepo;


	@GetMapping("/shoppingCart")
	public String shoppingCart(Model model, @AuthenticationPrincipal UserDetails userDetails,
			Product product, OrderHistory orderHistory,  @ModelAttribute("cart") ShoppingCart shoppingCart, BindingResult result) {
	
		String username = userDetails.getUsername();
		User user = userRepo.findByUserName(username);
		model.addAttribute("user", user);
		
		List<ShoppingCart> listShopCart = user.getShoppingCart();
//		if(user.getProduct().getId() != product.getId()) {
//			productRepo.delete(product);
//		}
		Double shoppingCartTotal = 0.0;
		
		for (ShoppingCart x : listShopCart) {
			shoppingCartTotal += x.getTotalCost();
		}

		model.addAttribute("listShopCart", listShopCart);
		model.addAttribute("shoppingCartTotal", shoppingCartTotal);
	//	model.addAttribute("listProduct", listProduct);
			
		List<MyOrder> myOrder = orderRepo.findAll();
		model.addAttribute("order", myOrder);

		List<OrderHistory> orderListHistory = orderHistRepo.findAll();
		model.addAttribute("orderListHistory", orderListHistory);
		
		// if product in sto is empty => delete product from shoppingCart
		/*
		 * List<ShoppingCart> list = shoppingCartRepo.findAll(); for (ShoppingCart
		 * shoppingCart2 : list) { if(shoppingCart2.getProduct().getStock()<=0) {
		 * shoppingCartRepo.delete(shoppingCart2); } }
		 */
		
		return "ShoppingCart";

	}

	// Save new Qty in Shopping Cart //

	@PostMapping("/saveQty")
	public String saveQty(MultipartFile file, @ModelAttribute("cart") ShoppingCart cart,
			@AuthenticationPrincipal UserDetails userDetails, User user, Model model) {
			
		ShoppingCart cart2 = shoppingCartRepo.getOne(cart.getId());	
		cart2.setQuantity(cart.getQuantity()); 

		Double sum = cart2.getPrice() * cart2.getQuantity();
		cart2.setSum(sum);

		shoppingCartRepo.save(cart2);
		model.addAttribute("cart", cart2);
		 
		return "redirect:/shoppingCart";
	}
	

	// Delete Product //

	@GetMapping("/deleteShopProduct/{id}")
	public String deleteProduct(@PathVariable("id") Integer id, ShoppingCart shoppingCart) {
		shoppingCartRepo.deleteById(id);
		return "redirect:/shoppingCart";
	}
	

	// Order Product //

	@GetMapping("/orderProduct/{id}")
	public String orderProduct(@PathVariable("id") Integer id, @AuthenticationPrincipal UserDetails userDetails,
			Product product, MyOrder order, Model model) {

		String userName = userDetails.getUsername();
		User user = userRepo.findByEmail(userName);

		ShoppingCart shoppingCart = shoppingCartRepo.findById(id).get();

		order.setId(shoppingCart.getId());
		order.setName(shoppingCart.getName());
		order.setAddress(shoppingCart.getAddress());
		order.setImage(shoppingCart.getImage());
		order.setPrice(shoppingCart.getPrice());
		order.setQuantity(shoppingCart.getQuantity());
		order.setSum(shoppingCart.getSum());
		order.setOrigin(shoppingCart.getOrigin());
		order.setSerialNumber(shoppingCart.getSerialNumber());
		order.setDescription(shoppingCart.getDescription());
		
		orderRepo.save(order);
	 
		return "redirect:/showOrder";
	}
	
	

}
