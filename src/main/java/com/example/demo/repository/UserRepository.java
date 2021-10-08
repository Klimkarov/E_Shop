package com.example.demo.repository;
import java.util.List;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Payment;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;

@Repository
@Transactional
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("SELECT u FROM User u WHERE "// e.firstName LIKE %?1%"
			//	+ " OR e.lastName LIKE %?1%"
			//	+ " OR e.email LIKE %?1%"
		    //    + " OR e.company LIKE %?1%"
		    //    + " OR e.salary LIKE %?1%"
		+ "CONCAT(u.id, u.image, u.userName, u.email, u.roles, u.created)"
		+ "LIKE %?1%") 
		public List<User> findAll(String keyword);

//	@Query("SELECT e FROM User e WHERE e.email = email")
	User findByEmail(String email);

	User findByUserName(String userName);
	
//	@Query("SELECT i FROM User i WHERE i.id = id")
//	User findUserById(Integer id);

	void save(Payment payment);

	boolean existsByEmailIgnoreCase(String email);

	

	

}
