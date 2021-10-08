package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.MyOrder;
import com.example.demo.entity.OrderHistory;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Product;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderHistoryRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
@Transactional
@Controller
public class OrderHistoryController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	PaymentRepository payRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	OrderHistoryRepository orderHistRepo;
	

	@GetMapping("/showOrderHistory")
	public String getOrder (Model model, @AuthenticationPrincipal UserDetails userDetails, 
			OrderHistory orderHistory, Payment payment, MyOrder myOrder, Product product) {

		String userName = userDetails.getUsername();
		User user = userRepo.findByUserName(userName);
		model.addAttribute("user", user);
		
        OrderHistory OH = new OrderHistory();
		OH.setUser(user);
		OH.setPayment(payment);
	
			
		List<OrderHistory> listOH = user.getOrderHistory();
		Double orderTotal = 0.0;
		
		for (OrderHistory x : listOH) {
			orderTotal += x.getQuantity()*x.getPrice();
		}
		
		model.addAttribute("orderTotal", orderTotal);
		model.addAttribute("listOH", listOH);
					
//		for (OrderHistory orderHistory2 : listOH) {
//			if(orderHistory2 != null) {
//				Double orTotal = 0.00;
//				 orTotal = orderHistory2.getTotal();
//				model.addAttribute("total", orTotal);
//			}
//			
//		}
		
		
		List<OrderHistory> orderHist = orderHistRepo.findAll();
		model.addAttribute("orderListHistory", orderHist);
		
//      Set User in Success Payment		
		List<Payment> payment1 = user.getPayment();
		model.addAttribute("listPayment", payment1);
			
		return "orderHistory";
	}
	
	
	@GetMapping("/deleteOrderHistoryById/{id}")
	public String deleteOrderHistory(@PathVariable ("id") Integer id) {
		
		orderHistRepo.deleteById(id);
	//	payRepo.deleteById(id);		
		return "redirect:/showOrderHistory";
	}
	
	
	@GetMapping("/getSum")
	public String getSum(Model model) {
		
		
		List<OrderHistory> orderHistSum = orderHistRepo.findAll();
		
		for (OrderHistory orderHistory2 : orderHistSum) {
			orderHistory2.setSum(orderHistory2.getPrice()*orderHistory2.getQuantity());
			orderHistRepo.save(orderHistory2.getSum());
		
		}
		
		List<OrderHistory> orderHistTotal = orderHistRepo.findAll();
		Double sum1 = orderHistTotal.stream().map(x -> x.getSum()).reduce((double) 0, Double::sum);
		Double totalOH = sum1;
		
		OrderHistory orderTotal = new OrderHistory();
		orderTotal.setTotal(sum1);
		orderHistRepo.save(orderTotal);
		model.addAttribute("total", totalOH);		

		
		return "redirect:/showOrderHistory";
	}

}
