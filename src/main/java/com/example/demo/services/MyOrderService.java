package com.example.demo.services;

import java.io.IOException;
import java.util.Base64;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import com.example.demo.entity.MyOrder;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.repository.MyOrderRepository;


@Service
@Transactional 
@Component
public class MyOrderService {
	
	@Autowired
	MyOrder myOrder;
	
	@Autowired
	MyOrderRepository myOrderRepo;
	
	public void saveImageOrder(MyOrder order, MultipartFile file) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.contains("..")) {
			System.out.println("not a valid file");
		}
		try {
			order.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {

			e.printStackTrace();
		}

		myOrderRepo.save(order);

	}

}
