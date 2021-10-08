package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Category;
import com.example.demo.entity.Role;

@Repository
@EnableJpaRepositories
public interface CategoryRepository extends JpaRepository<Category, Integer>{

//	boolean existsByProductCategory(String gender);

//	Category findByProductCategory(String gender);

	

//	@Query("SELECT c FROM Category c WHERE c.gender LIKE %?1%")
//	List<Category> findAll(String keyword);

}
