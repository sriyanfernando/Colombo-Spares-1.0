package com.javasampleapproach.mysql.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.mysql.model.Role;

@Repository("roleRepository")
public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByRole(String role);
	List<Role> findById(long id);
}
