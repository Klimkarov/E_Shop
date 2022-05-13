package com.example.demo.entity;

import java.time.LocalDate;




import java.util.Random;

import javax.persistence.CascadeType;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import javax.persistence.Table;



import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
   	private String name;
	private Double price;
	private String origin;
	private Integer stock;
	private Integer quantity;
	private LocalDate createdProduct;
	private String description;
	private Integer serialNumber;
	
//	Random random = new Random();
//	private Integer serialNumber = random.nextInt(100000);

//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private Date createdProduct;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "date", nullable=false)
//  private Date createdProduct;

//	  Date date = Date.from(java.time.ZonedDateTime.now().toInstant());
//	  Date createdDate = new Date();
	
    @ManyToOne(cascade = CascadeType.ALL) // za da se izbrise product od shoping cart 
	private ShoppingCart shoppingCart;    // niedna cascada ne raboti

	@Lob
	private String image;

	@ManyToOne
	Category category;

	

	public Product(Integer serialNumber, String name, Double price, String origin, Integer stock, Integer quantity,
			LocalDate createdProduct, String description, ShoppingCart shoppingCart, String image, Category category) {
		super();
		this.serialNumber = serialNumber;
		this.name = name;
		this.price = price;
		this.origin = origin;
		this.stock = stock;
		this.quantity = quantity;
		this.createdProduct = createdProduct;
		this.description = description;
		this.shoppingCart = shoppingCart;
		this.image = image;
		this.category = category;
	}



	@Override
	public String toString() {
		return "Product [id=" + id + ", serialNumber=" + serialNumber + ", name=" + name + ", price=" + price
				+ ", origin=" + origin + ", stock=" + stock + ", quantity=" + quantity + ", createdProduct="
				+ createdProduct + ", description=" + description + ", shoppingCart=" + shoppingCart + ", image="
				+ image + ", category=" + category + "]";
	}



	

}
