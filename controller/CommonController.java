package com.javasampleapproach.mysql.controller;

import java.util.Set;

import org.apache.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javasampleapproach.mysql.model.Customer;
import com.javasampleapproach.mysql.model.EntityAlreadyExistsException;
import com.javasampleapproach.mysql.service.CustomerService;

import com.javasampleapproach.mysql.validater.PasswordValidator;


@Controller
public class CommonController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping(value = { "/welcome" })
	public String welcome() {
		return "welcome";
	}

	static Logger log = Logger.getLogger(CommonController.class.getName());

	
	
	/**
	 * 
	 * @param customer
	 * @return
	 */
	
	@RequestMapping(value = "/api/register", method = RequestMethod.POST)
	public ResponseEntity<Customer> createNewCustomer(@RequestBody Customer customer)  throws NullPointerException
	{
		Customer customerExist = customerService.findCustomerByEmail(customer.getEmail());
	
		if (customerExist != null) 
		{
			log.warn("Account is already exists");
			throw new EntityAlreadyExistsException("email" + customer.getEmail());
			
		} 
		
		else 
		{
			/*
			if(!EmailValidator.validateEmail(customer.getEmail())) {
				log.warn("Telephone number must be 10 numbers");				
				return new ResponseEntity<Customer>(HttpStatus.RESET_CONTENT);
							
			}
			
			*/
			
			if(!PasswordValidator.validatePassword(customer.getPassword())) {
				log.warn("Password must be at least eight characters, one or more lower case an uppercase letter, one or more numbers");				
				return new ResponseEntity<Customer>(HttpStatus.RESET_CONTENT);
							
			}
			
			if(customer.getcustomerName().trim()=="")
			{
				log.error("Customer name shouldn't be empty!");
				return new ResponseEntity<Customer>(HttpStatus.RESET_CONTENT);
			}
			
			Customer newCustomer = new Customer();
			newCustomer.setCustomerName(customer.getcustomerName());
			newCustomer.setEmail(customer.getEmail());
			newCustomer.setActive(true);
			newCustomer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
		
			/*
			ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
			Validator validator = vf.getValidator();		
			Set<ConstraintViolation<Customer>> constraintViolations = validator
			        .validate(newCustomer);
			
			
			for (ConstraintViolation<Customer> cv : constraintViolations) {
			      log.error(String.format(
			          "Error here! property: [%s], value: [%s], message: [%s]",
			          cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
			      return new ResponseEntity<Customer>(customer, HttpStatus.NOT_ACCEPTABLE);
			}	
		
			*/
			newCustomer = customerService.saveCustomer(newCustomer);
		    log.info("The account has being created successfully!!!");
			return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);

		}
		
	}
	
	
	
	
	/*
	@RequestMapping(value = "/api/register", method = RequestMethod.POST)
	public ResponseEntity<Customer> createNewCustomer(@RequestBody Customer customer)  throws NullPointerException
	{
		
		Customer customerExist=customerService.findCustomerByNumber(customer.getcustomerNumber());
	
		if (customerExist != null) 
		{
			log.warn("Account is already exists");
			
			throw new EntityAlreadyExistsException("customerNumber" + customer.getcustomerNumber());
			
		} 
		
		else 
		{
			
			if(!PasswordValidator.validatePassword(customer.getPassword())) {
				log.warn("Password must be at least eight characters, one or more lower case an uppercase letter, one or more numbers");				
				return new ResponseEntity<Customer>(HttpStatus.RESET_CONTENT);
							
			}
			
			if(customer.getcustomerName().trim()=="")
			{
				log.error("Customer name shouldn't be empty!");
				return new ResponseEntity<Customer>(HttpStatus.RESET_CONTENT);
			}
			
			Customer newCustomer = new Customer();
			newCustomer.setCustomerName(customer.getcustomerName());
			newCustomer.setCustomerNumber(customer.getcustomerNumber());
			newCustomer.setEmail(customer.getEmail());
			newCustomer.setActive(true);
			newCustomer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
			
			
			ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
			Validator validator = vf.getValidator();		
			Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(newCustomer);
			
			
			for (ConstraintViolation<Customer> cv : constraintViolations) {
			      log.error(String.format(
			          "Error here! property: [%s], value: [%s], message: [%s]",
			          cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
			      return new ResponseEntity<Customer>(customer, HttpStatus.NOT_ACCEPTABLE);
			}	
			
		
			newCustomer = customerService.saveCustomer(newCustomer);
		    log.info("The account has being created successfully!!!");
			return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);

		}
		
	}
	*/
	
}
	