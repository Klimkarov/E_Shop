package com.example.demo.services;

import java.time.LocalDate;


import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.entity.Product;
import com.example.demo.entity.Role;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.entity.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShoppingCartRepository;

@Service
@Transactional
public class ShoppingCartService {
	
	
	@Autowired
	ShoppingCart shoppingCart;

	@Autowired
	ShoppingCartRepository shoppingCartRepo;
	
	@Autowired
	ProductRepository productRepo;
	

	public Iterable<ShoppingCart> findAll() {

		return shoppingCartRepo.findAll();
	}
	

	public ShoppingCart find(Integer proId, int i) {

		return shoppingCartRepo.findById(proId).get();
	}
	
	
	

	public ShoppingCart find(int id) {
		// TODO Auto-generated method stub
		return shoppingCartRepo.findById(id).get();
	}

	
	public List<ShoppingCart> listCartItems (User user){
		
		return shoppingCartRepo.findByUser(user);
	}

//   public void getQty(Integer q) {
//	    Integer quantity = 1;
//	  quantity+=q;
//   }

}
