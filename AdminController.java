package com.javasampleapproach.mysql.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javasampleapproach.mysql.model.Customer;
import com.javasampleapproach.mysql.model.EntityNotFoundException;
import com.javasampleapproach.mysql.model.Vehicle;

import com.javasampleapproach.mysql.service.CustomerService;

import com.javasampleapproach.mysql.service.VehicleService;

@Controller
public class AdminController {
	
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private VehicleService vehicleService;
	//private DeviceService deviceService;

	static Logger log = Logger.getLogger(AdminController.class.getName());
	
	@RequestMapping(value = "/admin/customer/{email}", method = RequestMethod.GET)
	public @ResponseBody Customer getCustomer1(@PathVariable("email") String email) {
		Customer customer = customerService.findCustomerByEmail(email);
		if (customer == null) {
			log.error("Customer Not Found");
			throw new EntityNotFoundException("email" + email);
		}
		
		customer.getcustomerName();
		customer.getPassword();
		customer.getEmail();
		return customer;
	}	
	

	@RequestMapping(value = "admin/list", method = RequestMethod.GET)
	public ResponseEntity<List<Customer>> getAll() {
		List<Customer> customer = customerService.getAll();
		if (customer.isEmpty()) {
			return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Customer>>(customer, HttpStatus.OK);
	}

	
	@RequestMapping(value = "admin/delete/{email}", method = RequestMethod.DELETE)
	public ResponseEntity<Customer> deleteCustomer(@PathVariable String email) {
		
		System.out.println("Fetching & Deleting User with email " + email);
		
		 Customer customer = customerService.findCustomerByEmail(email);
		 
		 if (customer == null) {
	            System.out.println("Unable to delete. Customer with id " + email + " not found");
	            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
	        }
		 
		 else
		 {
	        customerService.deleteByEmail(email);
	        return new ResponseEntity<Customer>(HttpStatus.OK);
		 }
	    }
	
	@RequestMapping(value = "admin/vehicle_list", method = RequestMethod.GET)
	public ResponseEntity<List<Vehicle>> getAllVehicle() {
	List<Vehicle> vehicles = vehicleService.getAllVehicle();
		if (vehicles.isEmpty()) {
			return new ResponseEntity<List<Vehicle>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Vehicle>>(vehicles, HttpStatus.OK);
	}  
	
	/*
	@RequestMapping(value = "admin/device_list", method = RequestMethod.GET)
	public ResponseEntity<List<Device>> getAllDevice() {
	List<Device> device = deviceService.getAllDevice();
		if (device.isEmpty()) {
			return new ResponseEntity<List<Device>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Device>>(device, HttpStatus.OK);
	}  
	*/

}
