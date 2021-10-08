package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.example.demo.entity.MyOrder;
import com.example.demo.entity.User;

@Repository
@Transactional
public interface MyOrderRepository extends JpaRepository<MyOrder, Integer> {

	@Query("SELECT o FROM MyOrder o WHERE o.id = id")
	MyOrder findOrderId(Integer id);
	
//	@Query("SELECT id FROM MyOrder")
//	List<MyOrder> findOrderAllId(Integer id);
	
	//Order findByOrder(Optional<Order> findById);

}
