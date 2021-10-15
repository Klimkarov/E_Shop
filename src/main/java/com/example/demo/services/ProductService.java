package com.example.demo.services;

import java.io.IOException;



import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Product;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShoppingCartRepository;



@Service
@Transactional 
@Component
public class ProductService implements ProductInterface{

	@Autowired
	ProductRepository productRepo;

	@Autowired
	CategoryRepository categoryRepo;
	
	@Autowired
	ShoppingCartRepository shopCartRepo;
	
	@Autowired
	Product product;
	
	public ProductService() {
		super();
	}
	
	public ProductService(Product product) {
		this.product = product;
	}
	
	public String getProductName() {
		return product.getName();
	}
	
	
	public Page<Product> findAll(int pageNumber, String keyword, String sortField, String sortDir) {
		
		Sort sort = Sort.by("name").ascending();
		sort = sortDir.equals("asc") ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
		        
		Pageable pageable = PageRequest.of(pageNumber - 1, 4, sort); // from 0 to 4 products on one page 1
		
		if(keyword != null) {
			return productRepo.findAll(keyword, pageable);
		}
		
		return productRepo.findAll(pageable);
	}
	
	
    public Product find(Integer proId, int i) {
		
		return productRepo.findById(proId).get();
	}
    
	@Override
	public Product find(int id) {
		// TODO Auto-generated method stub
		return productRepo.findById(id).get();
	}



	// get photo from pc and save in database//
	public void save(Product product, MultipartFile file) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.contains("..")) {
			System.out.println("not a valid file");
		}
		try {
			product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {

			e.printStackTrace();
		}

		productRepo.save(product);

	}

	public List<Product> listAll(String keyword) {
		// TODO Auto-generated method stub
		return (List<Product>) productRepo.findAll();
	}

	
	
	

}