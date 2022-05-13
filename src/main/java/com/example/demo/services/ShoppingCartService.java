package com.example.demo.services;

import java.io.IOException;

import java.util.Base64;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


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
	

	public void saveUpdateImage(ShoppingCart shoppingCart, MultipartFile file) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.contains("..")) {
			System.out.println("not a valid file");
		}
		try {
			shoppingCart.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {

			e.printStackTrace();
		}

		shoppingCartRepo.save(shoppingCart);

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
