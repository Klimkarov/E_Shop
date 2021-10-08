package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Category;
import com.example.demo.entity.Role;
import com.example.demo.entity.RoleName;

@Repository
@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<Role, Integer> {

	@Query("SELECT r FROM Role r WHERE r.name = ?1")
	Role findByName(String name);

	

//	void saveAll(Role admin, Role user, Role operator);
	
//	Role findByName(String roleUser);
//	Role findByRoleAdmin(RoleName roleAdmin);

}
