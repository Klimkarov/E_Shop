package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.demo.entity.Payment;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

//	@Query("delete FROM Payment p WHERE p.id = id")
//	Payment deletePaymentById(@Param("id")Integer id);



	

}
