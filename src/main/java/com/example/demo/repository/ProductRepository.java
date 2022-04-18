package com.example.demo.repository;




import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Product;



@Repository
@EnableJpaRepositories
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
	
	@Query("SELECT p FROM Product p WHERE "// p.firstName LIKE %?1%"
			//	+ " OR e.lastName LIKE %?1%"
			//	+ " OR e.email LIKE %?1%"
		    //    + " OR e.company LIKE %?1%"
		    //    + " OR e.salary LIKE %?1%"
		+ "CONCAT(p.serialNumber, p.name, p.price, p.origin, p.createdProduct, p.image,  p.category.gender, p.stock, p.description)"
		+ "LIKE %?1%") 
		public Page<Product> findAll(String keyword, Pageable pageable);
//                                                   Pageable za da bara na site strani
	
	
	
	public void save(List<Product> listKeyword);
	
	

	

}
