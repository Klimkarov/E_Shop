package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.OrderHistory;
import com.example.demo.entity.Product;

import com.example.demo.repository.OrderHistoryRepository;
import com.example.demo.repository.ProductRepository;

@Service
@Transactional
public class OrderHistoryService {
	
	

	@Autowired	
	OrderHistoryRepository orderHistRepo;
	
	@Autowired	
	ProductRepository productRepo;


	public OrderHistory getOrderHistId(Integer id) {
		 return orderHistRepo.findById(id).get();
	}

}
