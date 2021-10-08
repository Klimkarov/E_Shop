package com.example.demo.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.Transient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Transactional
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer serialNumber;
	
	private String name;
	
	private Double price;
	
	private String origin;
	
	private Integer stock;
	
	private Integer quantity;
	
	private Double sum;
	
	private String description;
	
	@Lob
	private String image;
	
	private LocalDate createdProduct;
	
	private Double total;
	
	@ManyToOne
	private Category category;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Product product;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;
	
	@ManyToOne
	private MyOrder order;

	public ShoppingCart(Integer serialNumber, String name, Double price, String origin, Integer stock, Integer quantity,
			Double sum, String description, String image, LocalDate createdProduct, Double total, Category category,
			Product product, User user, Address address, MyOrder order) {
		super();
		this.serialNumber = serialNumber;
		this.name = name;
		this.price = price;
		this.origin = origin;
		this.stock = stock;
		this.quantity = quantity;
		this.sum = sum;
		this.description = description;
		this.image = image;
		this.createdProduct = createdProduct;
		this.total = total;
		this.category = category;
		this.product = product;
		this.user = user;
		this.address = address;
		this.order = order;
	}

	@Override
	public String toString() {
		return "ShoppingCart [id=" + id + ", serialNumber=" + serialNumber + ", name=" + name + ", price=" + price
				+ ", origin=" + origin + ", stock=" + stock + ", quantity=" + quantity + ", sum=" + sum
				+ ", description=" + description + ", image=" + image + ", createdProduct=" + createdProduct
				+ ", total=" + total + ", category=" + category + ", product=" + product + ", user=" + user
				+ ", address=" + address + ", order=" + order + "]";
	}

	
	public Double getTotalCost() {
		return getQuantity() * product.getPrice();
	}


}
	


