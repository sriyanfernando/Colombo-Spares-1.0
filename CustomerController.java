package com.javasampleapproach.mysql.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.javasampleapproach.mysql.model.Customer;
import com.javasampleapproach.mysql.model.EntityNotFoundException;
import com.javasampleapproach.mysql.service.CustomerService;
import com.javasampleapproach.mysql.validater.PasswordValidator;


@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	
    
	static Logger log = Logger.getLogger(CustomerController.class.getName());
	
	/**
	 * 
	 * @param auth
	 * @return
	 */

	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public ResponseEntity<Customer> getCustomer(Authentication auth)
	{
		
		Customer customer = customerService.findCustomerByEmail(auth.getName());
		//Customer customer= customerService.findCustomerByNumber(auth.getName());
		if (customer == null) 
		{
			log.error("Account with " +auth.getName()+ " does not exist!!!");
			throw new EntityNotFoundException("email" + auth.getName());
		} 
		
		else 
		{
			if(customer.getActive() == false)
			{
				log.error("Account with " +auth.getName()+ " has being disabled!!!");
				return new ResponseEntity<Customer>(customer, HttpStatus.PRECONDITION_FAILED);
			}
			else
				log.info("Returned customer details: "+ "[customer: " +customer.getcustomerName()+ "]");
				return new ResponseEntity<Customer>(customer, HttpStatus.OK);	
				
		}
	}

	/**
	 * 
	 * @param customer
	 * @param auth
	 * @return
	 */
	
	
	@RequestMapping(value = "/customer/update", method = RequestMethod.PUT)
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, Authentication auth) 
	{
		try
		{
		Customer existingCustomer = customerService.findCustomerByEmail(auth.getName());
		
		if (existingCustomer == null) 
		{
			log.warn("Account with " +auth.getName()+ " doesn't exist!!!");
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		} 
		
		else {
			if(customer.getcustomerName()==null || customer.getcustomerName().trim()=="")
				{
					log.error("Required fields can't be null or empty!!!");
					return new ResponseEntity<Customer>(existingCustomer,HttpStatus.RESET_CONTENT);
				}
		
					existingCustomer.setCustomerName(customer.getcustomerName());
					customerService.saveCustomer(existingCustomer);
					log.info("Updated successfully!!!");
			return new ResponseEntity<Customer>(existingCustomer,HttpStatus.OK);
			}
		
		}
		catch (Exception ex){
			log.error(ex);
			return new ResponseEntity<Customer>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	/**
	 * 
	 * @param customer
	 * @param auth
	 * @return
	 */
	
	
	
	@RequestMapping(value ="/customer/change_password", method = RequestMethod.PUT)
	public ResponseEntity<Customer> changePassword(@RequestBody Customer customer, Authentication auth)
	{
		try {
		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
		
		if(thisCustomer==null)
		{
			log.warn("Account with " +auth.getName()+ " doesn't exist!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			
			if(!PasswordValidator.validatePassword(customer.getPassword())) {
					log.warn("Password must be at least eight characters, one or more lower case an uppercase letter, one or more numbers");
					return new ResponseEntity<Customer>(customer, HttpStatus.RESET_CONTENT);
				}
				
				thisCustomer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
				thisCustomer = customerService.saveCustomer(thisCustomer);
				log.info("Password updated successfully!!!");
				return new ResponseEntity<Customer>(thisCustomer, HttpStatus.OK);
			
			
			}
		}
		catch (Exception ex)
		{
			log.error(ex);
			return new ResponseEntity<Customer>(HttpStatus.BAD_REQUEST);
		}
	}
	

	
	
	

}
