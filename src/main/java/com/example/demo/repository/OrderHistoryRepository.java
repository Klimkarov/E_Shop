package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.OrderHistory;

@Repository
@EnableJpaRepositories
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Integer>{

	void save(Double sum);

//	@Query("SELECT h FROM OrderHistory h WHERE h.serialNumber = serialNumber")
//	OrderHistory GetOrderHistoryId(Integer id);

//	public OrderHistory getOrderHistId(Integer id);

}
