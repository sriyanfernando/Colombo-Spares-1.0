//inject the UserRepository, RoleRepository and the BCryptPasswordEncoder into our service implementation.

package com.javasampleapproach.mysql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasampleapproach.mysql.model.Customer;
import com.javasampleapproach.mysql.model.Role;
import com.javasampleapproach.mysql.repository.AdminRepository;
import com.javasampleapproach.mysql.repository.CustomerRepository;
import com.javasampleapproach.mysql.repository.RoleRepository;


import java.util.List;



@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AdminRepository adminRepository;  
	
	@Autowired
	private RoleRepository roleRepository;
	

	@Override
	public Customer findCustomerByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	
	@Override
	public Customer saveCustomer(Customer customer) {
		customer.setActive(customer.getActive());
		Role role = roleRepository.findByRole("CUSTOMER");
		customer.setRoles(role);
		return customerRepository.save(customer);
	}

	@Override
	public List<Customer> getAll() {
		return customerRepository.findAll();
	}

	
	@Override
	public void deleteByEmail(String email) {
		adminRepository.deleteByEmail(email);	
	}

	
	@Override
	public Customer findCustomerById(Long customerId) {
		return findCustomerById(customerId);
	}

	/*
    //NEW
	@Override
	public Customer findCustomerByNumber(String customerNumber) {
		
		return customerRepository.findByCustomerNumber(customerNumber);
	}
	*/
}