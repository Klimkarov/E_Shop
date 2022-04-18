package com.example.demo.entity;

import java.util.HashSet;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="roles")
	private Integer id;
	
	@Column(nullable = false, length = 45)
	private String name;
	
//	private RoleName roleName;
	
	
	public Role(String name) {
		this.name = name;
	}
	
	public Role(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.name;
	}

	
//	@Enumerated(EnumType.STRING)
//	@NaturalId
//	@Column(length = 60)
//	private RoleName role;



}
