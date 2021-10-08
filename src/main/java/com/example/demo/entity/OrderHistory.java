package com.example.demo.entity;

import java.time.LocalDate;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.internal.build.AllowSysOut;

import java.beans.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistory {

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
	
	private String productName;
		
	private Double total;
	
	private LocalDateTime ordered;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Product product;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Payment payment;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private User user;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private MyOrder myOrder;

	
	public OrderHistory(Integer serialNumber, String name, Double price, Double sum, String origin, Integer quantity,
			String description, LocalDate date, String image, String productName, Double total, LocalDateTime ordered,
			Product product, Payment payment, User user, MyOrder myOrder) {
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
		this.productName = productName;
		this.total = total;
		this.ordered = ordered;
		this.product = product;
		this.payment = payment;
		this.user = user;
		this.myOrder = myOrder;
	}

	@Override
	public String toString() {
		return "OrderHistory [id=" + id + ", serialNumber=" + serialNumber + ", name=" + name + ", price=" + price
				+ ", sum=" + sum + ", origin=" + origin + ", quantity=" + quantity + ", description=" + description
				+ ", date=" + date + ", image=" + image + ", productName=" + productName + ", total=" + total
				+ ", ordered=" + ordered + ", product=" + product + ", payment=" + payment + ", user=" + user
				+ ", myOrder=" + myOrder + "]";
	}
	
	
	@Transient
	public Double getTotalCost() {
		return getQuantity() * product.getPrice();
	}
	
}
