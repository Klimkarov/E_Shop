package com.example.demo.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="MyOrder")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer serialNumber;

	private String name;

	private Double price;

	private Double sum;

	private String origin;

	private Integer quantity;

	private String description;

	private LocalDate date;

	@Lob
	private String image;

	@ManyToOne
	Address address;

//	@OneToOne
//	User user;

	@ManyToOne
	Product product;

	@ManyToOne
	ShoppingCart shopingCart;

	public MyOrder(Integer serialNumber, String name, Double price, Double sum, String origin, Integer quantity,
			String description, LocalDate date, String image, Address address, User user, Product product,
			ShoppingCart shopingCart) {
		super();
		this.serialNumber = serialNumber;
		this.name = name;
		this.price = price;
		this.sum = sum;
		this.origin = origin;
		this.quantity = quantity;
		this.description = description;
		this.date = date;
		this.image = image;
		this.address = address;
	//	this.user = user;
		this.product = product;
		this.shopingCart = shopingCart;
	}

	@Override
	public String toString() {
		return "MyOrder [id=" + id + ", serialNumber=" + serialNumber + ", name=" + name + ", price=" + price + ", sum="
				+ sum + ", origin=" + origin + ", quantity=" + quantity + ", description=" + description + ", date="
				+ date + ", image=" + image + ", address=" + address + ", product=" + product
				+ ", shopingCart=" + shopingCart + "]";
	}

	

	
	
	

}