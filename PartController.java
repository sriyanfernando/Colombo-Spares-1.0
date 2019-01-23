package com.javasampleapproach.mysql.controller;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javasampleapproach.mysql.model.Customer;
import com.javasampleapproach.mysql.model.Device;
import com.javasampleapproach.mysql.model.Part;
import com.javasampleapproach.mysql.model.Peripheral;
import com.javasampleapproach.mysql.model.Vehicle;
import com.javasampleapproach.mysql.service.CustomerService;
import com.javasampleapproach.mysql.service.PartService;
import com.javasampleapproach.mysql.service.VehicleService;

@Controller
public class PartController 
{
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private PartService partService;
	

	static Logger log = Logger.getLogger(PartController.class.getName());
	
	/**
	 * 
	 * @param vehicleId
	 * @param auth
	 * @return  adding parts to the table
	 */
	
	
	@RequestMapping(value = "/customer/add_part/", method = RequestMethod.POST)
	public ResponseEntity<Part> addPart(@RequestBody Part part,@RequestParam(name = "vehi_id", required = true)Long vehicleId, Authentication auth) 
	{

	Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());        
	Vehicle thisVehicle = vehicleService.findVehicleById(vehicleId,thisCustomer);       

	if(thisVehicle==null)
	{
		log.error("Vehicle not found!!!");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				
	}
	Part existingPart=partService.findPartById(part.getPartId(),thisVehicle);
			
	if (existingPart != null) {
		log.error("Part Already Exists!!!");
		return new ResponseEntity<Part>(existingPart, HttpStatus.FOUND);
	}
	else
	{
	
		Part newPart= new Part();
		newPart.setPartName(part.getPartName());
		newPart.setPartType(part.getPartType());
		newPart.setPartYear(part.getPartYear());
		newPart.setPartPrice(part.getPartPrice());
		newPart.setPartLocation(part.getPartLocation());
		newPart.setVehicle(thisVehicle);
		newPart=partService.savePart(newPart);
		log.info("Part name:"+part.getPartName()+" added successfully!!!");
		return new ResponseEntity<Part>(HttpStatus.CREATED);
		
		
		
	}	
		
				
	}

	/**
	 * 
	 * @param vehicleId
	 * @param auth
	 * @return collection of parts
	 */
	@RequestMapping(value = "/customer/get_parts", method = RequestMethod.GET)
	public ResponseEntity<Collection<Part>> get_parts(Authentication auth,
			@RequestParam(name = "vehi_id", required = true) Long vehicleId) {

		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());

		if (thisCustomer == null) {
			log.error("Customer not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		
		Vehicle thisVehicle=vehicleService.findVehicleById(vehicleId, thisCustomer);
	

		if (thisVehicle == null) {
			log.error("Vehicle not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		log.info("Returned periperal list: " + "[vehicle name:" + thisVehicle.getVehicleName() + "]");
		return new ResponseEntity<>(thisVehicle.getParts(), HttpStatus.OK);

	}
	
	
	
	
	@RequestMapping(value = "/customer/get_part", method = RequestMethod.GET)
	public ResponseEntity<Part> get_part(Authentication auth, @RequestParam(name = "vehi_id", required = true) Long vehicleId,
			@RequestParam(name = "part_id", required = true) Long partId) {
		
		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());

		if (thisCustomer == null) {
			log.error("Customer not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
				
		Vehicle thisVehicle=vehicleService.findVehicleById(vehicleId, thisCustomer);

		if (thisVehicle == null) {
			log.error("vehicle not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Part thisPart=partService.findPartById(partId, thisVehicle);
	

		if (thisPart == null) {
			log.error("Part  not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		log.info("Returned periperal details: " + "[vehicle name:" + thisVehicle.getVehicleName() + "]"
				+ "  [part name:" + thisPart.getPartName() + "]");
		return new ResponseEntity<>(thisPart, HttpStatus.OK);
	}
	
	
	
}
