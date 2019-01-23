//user service layer(interface and implementation)

package com.javasampleapproach.mysql.service;



import java.util.List;

import com.javasampleapproach.mysql.model.Customer;

public interface CustomerService {
	
	public Customer findCustomerByEmail(String email);
	
	
	public Customer findCustomerById(Long customerId);
	public Customer saveCustomer(Customer customer);
	List<Customer> getAll();
	void deleteByEmail(String email);
	
	//public Customer findCustomerByNumber(String customerNumber); // new

	

}

