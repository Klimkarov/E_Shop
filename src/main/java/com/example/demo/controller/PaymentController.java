package com.example.demo.controller;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Address;
import com.example.demo.entity.MyOrder;
import com.example.demo.entity.OrderHistory;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Product;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderHistoryRepository;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.MyOrderRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShoppingCartRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.OrderHistoryService;

@Controller
@Transactional // for image
public class PaymentController {

	@Autowired
	PaymentRepository payRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	MyOrderRepository orderRepo;

	@Autowired
	OrderHistoryRepository orderHistRepo;

	@Autowired
	ProductRepository productRepo;

	@Autowired
	ShoppingCartRepository shoppingCartRepo;

	@Autowired
	OrderHistoryService orderHistoryService;

	@Autowired
	AddressRepository addressRepo;

	@PostMapping("/payment")
	public String savePayMent(Model model, @ModelAttribute("payment") Payment payment, Product product, MyOrder order,
			OrderHistory orderHistory, @AuthenticationPrincipal UserDetails userDetails, Address address) {

		String userName = userDetails.getUsername();
		User user = userRepo.findByUserName(userName);
		
//      Set User in Success Payment		
		user.setAddress(payment.getAddress());
		payment.setUser(user);
		payRepo.save(payment);
		userRepo.save(payment);

//      get Total from MyOrder to Payment - successPayment

		List<MyOrder> payTotal = orderRepo.findAll();
		Double sum = payTotal.stream().map(x -> x.getSum()).reduce((double) 0, Double::sum);
		Double total = sum;
		payment.setTotal(total);
		payRepo.save(payment);

//      process Payment, to full OrderHistory //

		List<MyOrder> order1 = orderRepo.findAll();

		Double sum1 = order1.stream().map(x -> x.getSum()).reduce((double) 0, Double::sum);
		Double total1 = sum1;

		for (MyOrder myOrder : order1) {

			if (payment != null) {
				OrderHistory OH = new OrderHistory();
				OH.setUser(user);
				OH.setPayment(payment);
				// OH.setProduct(product);

				LocalDate date = LocalDate.now();
				OH.setDate(date);

				OH.setId(myOrder.getId());
				OH.setImage(myOrder.getImage());
				OH.setName(myOrder.getName());
				OH.setPrice(myOrder.getPrice());
				OH.setSerialNumber(myOrder.getSerialNumber());
				OH.setQuantity(myOrder.getQuantity());
				OH.setDescription(myOrder.getDescription());
				OH.setSum(OH.getPrice() * OH.getQuantity());
				OH.setPayment(payment);
				orderHistRepo.save(OH);
				// user.setOrderHistory(orderHistory);

				
//				set total for orderHistory 
				
				List<OrderHistory> orderTotal = user.getOrderHistory();
				Double sumOrder = orderTotal.stream().map(x -> x.getSum()).reduce((double) 0, Double::sum);
				Double totalOrder = sumOrder;
				OH.setTotal(totalOrder);
				orderHistRepo.save(OH);
				model.addAttribute("total", totalOrder);

				
//              process Payment, set Stock

				List<Product> productStock = (List<Product>) productRepo.findAll();
				for (Product product2 : productStock) {
					if (product2.getSerialNumber().equals(myOrder.getSerialNumber())) {

						product2.setStock(product2.getStock() - myOrder.getQuantity());
						productRepo.save(product2);
					}
				}
			}

			
//        process Payment, delete ShoppingCart and Order //

			List<ShoppingCart> listShopCart1 = shoppingCartRepo.findAll();
			for (ShoppingCart shoppingCart : listShopCart1) {
				if (payment != null && shoppingCart.getSerialNumber().equals(myOrder.getSerialNumber())) {
					shoppingCartRepo.delete(shoppingCart);
					orderRepo.delete(myOrder);
				}
			}
		}

		return "redirect:/showPayment";
	}

	@GetMapping("/showPayment")
	public String getPayment(Model model, @AuthenticationPrincipal UserDetails userDetails, OrderHistory OH) {

		String userName = userDetails.getUsername();
		User user = userRepo.findByUserName(userName);
		model.addAttribute("user", user);

//      Set User in Success Payment		
		List<Payment> payment1 = user.getPayment();
		model.addAttribute("listPayment", payment1);
		return "successPayment";
	}
	


}
