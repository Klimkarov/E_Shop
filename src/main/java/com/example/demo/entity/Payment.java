package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String payMethod;
	
	private String cardNumber;
	
//	@Column(name="expires", unique = true, length = 5)
//	@NumberFormat(pattern="MM/yyyy")
	private String expires;
	
	@Column(name="cvv", length=3)
	private Integer cvv;
	
	private Double total;
	
//	@ManyToOne
//	@JoinColumn(referencedColumnName = "id")
//	private Product product;
	
		
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private User user;
	
	@OneToOne
	MyOrder Order;
	
	@ManyToOne(cascade = CascadeType.ALL)
	Address address;

	public Payment(String payMethod, String cardNumber, String expires, Integer cvv, Double total, User user,
			MyOrder order, Address address) {
		super();
		this.payMethod = payMethod;
		this.cardNumber = cardNumber;
		this.expires = expires;
		this.cvv = cvv;
		this.total = total;
		this.user = user;
		Order = order;
		this.address = address;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", payMethod=" + payMethod + ", cardNumber=" + cardNumber + ", expires=" + expires
				+ ", cvv=" + cvv + ", total=" + total + ", user=" + user + ", Order=" + Order + ", address=" + address
				+ "]";
	}

	

	

	

	


}
