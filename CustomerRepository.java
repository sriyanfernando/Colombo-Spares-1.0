package com.javasampleapproach.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.mysql.model.Customer;
// Repository is java interface(not a class)
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
	Customer findByEmail(String email);
	//Customer findByCustomerNumber(String customerNumber);
}

