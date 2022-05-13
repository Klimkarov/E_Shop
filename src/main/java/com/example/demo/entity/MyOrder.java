package com.example.demo.entity;

import java.time.LocalDate;


import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class MyOrder {

	@Id
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

	@Override
	public String toString() {
		return "MyOrder [id=" + id + ", serialNumber=" + serialNumber + ", name=" + name + ", price=" + price + ", sum="
				+ sum + ", origin=" + origin + ", quantity=" + quantity + ", description=" + description + ", date="
				+ date + ", image=" + image + ", address=" + address + ", product=" + product + ", shopingCart="
				+ shopingCart + "]";
	}

	
	
	
	
	
}