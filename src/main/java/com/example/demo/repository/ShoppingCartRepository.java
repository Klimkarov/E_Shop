package com.example.demo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MyOrder;
import com.example.demo.entity.Product;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.entity.User;


@Repository
@EnableJpaRepositories
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
	
	public List<ShoppingCart> findByUser(User user);

	
	
//	public List<ShoppingCart> deleteByUserAndProduct(User user, Product product);
	
	@Query("SELECT q FROM ShoppingCart q WHERE q.quantity = cart")
	public List<ShoppingCart> getQty(Integer qty);

	public void save(Integer qty);

	public void save(Double total);

	public List<ShoppingCart> findAllById(Integer id);
		
	// for delete no  need alias after delete
//	@Query("DELETE FROM ShoppingCart n WHERE n.name =:name ")
//	public ShoppingCart deleteByName(String name);

//	@Query("SELECT i FROM ShoppingCart i WHERE i.id =:id ")
//	public List<ShoppingCart> findShopCartId(Integer id);
	
//	@Query("DELETE e FROM ShoppingCart e WHERE e.id = id")
//	public ShoppingCart  deletelistShopCart1ById(Integer id);
		
	

}
