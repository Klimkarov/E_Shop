package com.example.demo.controller;

import java.time.LocalDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Category;
import com.example.demo.entity.MyOrder;
import com.example.demo.entity.Product;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.MyOrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShoppingCartRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.CategoryService;
import com.example.demo.services.ProductService;
import com.example.demo.services.ShoppingCartService;

@Transactional
@Controller
public class ProductController {

	@Autowired
	MyOrderRepository orderRepo;

	@Autowired
	ProductRepository productRepo;

	@Autowired
	ProductService productService;

	@Autowired
	CategoryRepository categoryRepo;

	@Autowired
	CategoryService categoryService;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ShoppingCartRepository shoppingCartRepo;
	
	@Autowired
	ShoppingCartService shoppingCartService;
	
	@Autowired
	ShoppingCart shoppingCart;

	

	@GetMapping("/product")
	public String viewProductPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
			
		String keyword = ""; 			
					
		
		return listByPage(model, 1, keyword, "name", "asc");

	}


	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable ("pageNumber") int currentPage, 
			@Param("keyword") String keyword,
			@Param("sortField") String sortField,
			@Param("sortDir") String sortDir) {
		
		Page<Product> page = productService.findAll(currentPage, keyword, sortField, sortDir );

	//	int currentPage = 1;
		long totalItems = page.getTotalElements(); // dava vk. br. na vneseni produckti
		int totalPages = page.getTotalPages();     // vk. br. na strani

		List<Product> listProduct = page.getContent();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);
        
		Product product = new Product();
		model.addAttribute("product", product);	
		
		List<Category> listCategory = categoryRepo.findAll();
		model.addAttribute("listCategory", listCategory);
		
		return "product";
		
	}
	

	// Create new Product page for creation of new Product//

	@GetMapping("/showNewProductForm")
	public String showNewProduct(Model model, @AuthenticationPrincipal UserDetails userDetails) {

		Product product = new Product();
		model.addAttribute("product", product);

		List<Product> listProduct = (List<Product>) productRepo.findAll();
		model.addAttribute("listProduct", listProduct);

		Category category = new Category();
		model.addAttribute("category", category);

		List<Category> listCategory = categoryRepo.findAll();
		model.addAttribute("listCategory", listCategory);
			
	   // for profile image in nav bar
       String userName = userDetails.getUsername();
       User userImage = userRepo.findByUserName(userName);
	   model.addAttribute("userImage", userImage);

		return "new_product";
	}
	

	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute("product") Product product, @ModelAttribute("cart") ShoppingCart cart, Model model, 
			@AuthenticationPrincipal UserDetails userDetails, MultipartFile file) {

		LocalDate createdProduct = LocalDate.now();
		product.setCreatedProduct(createdProduct);
		
//		Integer sNumber = 0;
//		sNumber = product.getSerialNumber();
//
//// warning for duplicate SerialNumber
//		List<Product> listProduct = (List<Product>) productRepo.findAll();
//		for (Product product2 : listProduct) {
//			if(product2.getSerialNumber()==sNumber) {
//				return "warning_SerialNumber";
//			}
//		}
		
		productRepo.save(product);
		productService.save(product, file);
			
// if have updating on product, this is for also to automatic update in shoppingCart				
		String username = userDetails.getUsername();
		User user = userRepo.findByUserName(username);
		
		List<ShoppingCart> listShopCart =  shoppingCartRepo.findAll();
		for (ShoppingCart shoppingCart1 : listShopCart) {
			
			if(product.getSerialNumber()==shoppingCart1.getSerialNumber()) {
				
				ShoppingCart shopcart = new ShoppingCart();
				
				shopcart.setCategory(product.getCategory());
				shopcart.setCreatedProduct(product.getCreatedProduct());
				shopcart.setDescription(product.getDescription());
				shopcart.setId(product.getId());
				shopcart.setImage(product.getImage());
				shopcart.setName(product.getName());
				shopcart.setOrigin(product.getOrigin());
				shopcart.setPrice(product.getPrice());
				
				shopcart.setQuantity(1);
				shopcart.setAddress(shopcart.getAddress());
				shopcart.setTotal(shopcart.getTotal());
				
				shopcart.setUser(user);
				shopcart.setProduct(product);
				
				Double sum = shopcart.getPrice() * shopcart.getQuantity();
				shoppingCart.setSum(sum);
				shoppingCartRepo.save(shopcart);
				shoppingCartService.saveUpdateImage(shopcart, file);
							
			}	
		}			
	
// Warning for Stock or Price when they are equal with zero			
	List<Product> list = (List<Product>) productRepo.findAll();
	for (Product productStock : list) {	
		if(productStock.getStock() == 0 || productStock.getPrice() == 0) {
			productRepo.delete(product);
			return "warning_StockAndPrice";		
		}	
	}
	
	return "redirect:/product";
}
		

	// Update Product Page

	@GetMapping("/showUpdateProductForm/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model, 
			Category category, MultipartFile file, @AuthenticationPrincipal UserDetails userDetails) {

		Product product = productRepo.findById(id).get();
		model.addAttribute("product", product);

		//	model.addAttribute("category", product.getCategory());
			
		List<Category> listCategory = categoryRepo.findAll();
		model.addAttribute("listCategory", listCategory);
		
		// for profile image in nav bar
		String userName = userDetails.getUsername();
		User userImage = userRepo.findByUserName(userName);
		model.addAttribute("userImage", userImage);
	

		return "update_product";
	}

	
	
	// Button Delete Product By Id

	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable("id") Integer id ) {
		
		Product productId = productRepo.findById(id).get();
		
				
		List<ShoppingCart> listShoppingCart = shoppingCartRepo.findAll();
		for (ShoppingCart shoppingCart : listShoppingCart) {
			if(productId.getId()==shoppingCart.getId()) {
				productRepo.delete(productId);
				shoppingCartRepo.delete(shoppingCart);
			}
		}
		
		productRepo.delete(productId);
		
    	return "redirect:/product";
	}

	// Add Product to Shoping Card 

	@GetMapping("/addProduct/{proId}")
	public String addProducts(@PathVariable("proId") Integer proId, @AuthenticationPrincipal UserDetails userDetails, Model model) {


		String userName = userDetails.getUsername();
		User user = userRepo.findByUserName(userName);
		
		LocalDate addedProduct = LocalDate.now();

		Product productId = productRepo.findById(proId).get();
	//	User user1 = userRepo.findById(proId).get();
		
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setId(productId.getId());
		shoppingCart.setName(productId.getName());
		shoppingCart.setPrice(productId.getPrice());
		shoppingCart.setStock(productId.getStock());
		shoppingCart.setOrigin(productId.getOrigin());
		shoppingCart.setImage(productId.getImage());
		shoppingCart.setSerialNumber(productId.getSerialNumber());
		shoppingCart.setCreatedProduct(addedProduct);
		shoppingCart.setDescription(productId.getDescription());
	
		shoppingCart.setQuantity(1);
		shoppingCart.setAddress(shoppingCart.getAddress());
		shoppingCart.setTotal(shoppingCart.getTotal());
		shoppingCart.setCategory(productId.getCategory());
	//	shoppingCart.setUser(user1);

	//	user.setShoppingCart(shoppingCart);
		
		// Setiranje na Sum, odkako gi ima Price i Quantity 
		Double sum = shoppingCart.getPrice() * shoppingCart.getQuantity();
		shoppingCart.setSum(sum);
		
		shoppingCart.setUser(user);
		shoppingCart.setProduct(productId);
		shoppingCartRepo.save(shoppingCart);
		
		List<ShoppingCart> listShopCart = shoppingCartRepo.findAll();
		model.addAttribute("listShopCart", listShopCart);
		model.addAttribute("user", user);

		return "redirect:/shoppingCart";

	}

	@GetMapping("/buyProduct/{id}")
	public String orderProduct(@PathVariable("id") Integer id, @AuthenticationPrincipal UserDetails userDetails,
			Product product, MyOrder order, Model model) {

		String email = userDetails.getUsername();
		User user = userRepo.findByEmail(email);

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

	@GetMapping("/showDescription")
	public String getDescription(Model model) {

		List<Product> listProduct = (List<Product>) productRepo.findAll();
		model.addAttribute("listProduct", listProduct);
		
		// for profile image in nav bar
		List<User> listUser =  userRepo.findAll();
		model.addAttribute("listUser", listUser);

		return "description";
	}

	@GetMapping("/getDescription/{id}")
	public String getDescription(@PathVariable("id") Integer id) {

		Product product = productRepo.findById(id).get();

		productRepo.save(product);

		return "redirect:/showDescription";

	}



}
