package com.javasampleapproach.mysql.controller;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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

import com.javasampleapproach.mysql.model.EntityAlreadyExistsException;
import com.javasampleapproach.mysql.model.Part;
import com.javasampleapproach.mysql.model.Vehicle;
import com.javasampleapproach.mysql.service.CustomerService;

import com.javasampleapproach.mysql.service.VehicleService;

@Controller
public class VehicleController
{


	@Autowired
	private CustomerService customerService;

	@Autowired
	private VehicleService vehicleService;
	

	
	static Logger log = Logger.getLogger(VehicleController.class.getName());
	
	/**
	 * 
	 * @param vehicle
	 * @param auth
	 * @return 
	 */
	
	/*
	
	@RequestMapping(value = "/customer/add_vehicle", method = RequestMethod.POST)
	public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle, Authentication auth) 
	{

		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());

		Vehicle existingVehicle = vehicleService.findVehicleByVehicleModelAndCustomer(vehicle.getVehicleModel(),thisCustomer);       

		if (existingVehicle != null) {
			log.error("Vehicle already exist!!!");
			throw new EntityAlreadyExistsException("vehicle : " + existingVehicle);
		} 
		else {
			Vehicle newVehicle= new Vehicle();
			newVehicle.setVehicleName(vehicle.getVehicleName());
			if(vehicle.getVehicleModel()==null)
			{
				log.error("vehicle model can't be empty!!!");
				return new ResponseEntity<Vehicle>(HttpStatus.RESET_CONTENT);
			}
			newVehicle.setVehicleModel(vehicle.getVehicleModel());
			newVehicle.setVehicleType(vehicle.getVehicleType());
			newVehicle.setVehicleYear(vehicle.getVehicleYear());
			//newVehicle.setParts(vehicle.getParts());
			newVehicle.setCustomer(thisCustomer);
			newVehicle = vehicleService.saveVehicle(newVehicle);
			log.info("Vehicle name:"+vehicle.getVehicleName()+" added successfully!!!");
			return new ResponseEntity<Vehicle>(HttpStatus.CREATED);
			
		
		}
	}
	
	*/
	
	
	
	@RequestMapping(value = "/customer/add_vehicle", method = RequestMethod.POST)
	public ResponseEntity<Vehicle> addVehicleByModel(@RequestBody Vehicle vehicle, Authentication auth) 
	{

		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
       // Vehicle existingVehicle= vehicleService.findVehicleById(vehicle.getVehicleId(), thisCustomer);
  Vehicle existingVehicle = vehicleService.findVehicleByType(vehicle.getVehicleType(), thisCustomer); 

		if (existingVehicle != null) {
		    log.error("Vehicle already exist!!!");
			throw new EntityAlreadyExistsException("vehicle : " + existingVehicle);
		} 
		else {
			Vehicle newVehicle= new Vehicle();
			newVehicle.setVehicleName(vehicle.getVehicleName());
			if(vehicle.getVehicleModel()==null)
			{
				log.error("vehicle model can't be empty!!!");
				return new ResponseEntity<Vehicle>(HttpStatus.RESET_CONTENT);
			}
			newVehicle.setVehicleModel(vehicle.getVehicleModel());
			newVehicle.setVehicleType(vehicle.getVehicleType());
			newVehicle.setVehicleYear(vehicle.getVehicleYear());
			newVehicle.setCustomer(thisCustomer);
		
			newVehicle = vehicleService.saveVehicle(newVehicle);
			log.info("Vehicle name:"+vehicle.getVehicleName()+" added successfully!!!");
			return new ResponseEntity<Vehicle>(HttpStatus.CREATED);
			
		
		}
	}
	
	

	
	

	
	
	
	
	

	/**
	 * 
	 * @param auth
	 * @return
	 */

	
	@RequestMapping(value = "/customer/get_vehicle_list", method = RequestMethod.GET)
	public ResponseEntity<Collection<Vehicle>> getVehicles(Authentication auth)
	{
		Customer existingCustomer = customerService.findCustomerByEmail(auth.getName());
		//Vehicle thisVehicle = vehicleService.findVehicleByName(name, existingCustomer);
		log.info("Returned Vehicles list: " + "[customer:" +existingCustomer.getcustomerName() +"]");
		return new ResponseEntity<>(existingCustomer.getVehicles(), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param auth
	 * @return
	 */

	
	@RequestMapping(value = "/customer/get_distinct_vehicle_list", method = RequestMethod.GET)
	public ResponseEntity<Collection<Vehicle>> getDistinctVehicles(Authentication auth)
	{
		Customer existingCustomer = customerService.findCustomerByEmail(auth.getName());
		List<Vehicle> distinctVehicle=vehicleService.getAllDistinctVehicle();
		//Vehicle thisVehicle = vehicleService.findVehicleByName(name, existingCustomer);
		log.info("Returned Vehicles list: " + "[customer:" +existingCustomer.getcustomerName() +"]");
		return new ResponseEntity<>(distinctVehicle, HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @param auth
	 * @param vehicle model
	 * @return
	 */
	
	@RequestMapping(value = "/customer/get_vehicle", method = RequestMethod.GET)
	public ResponseEntity<Vehicle> getVehicleByModel(Authentication auth,@RequestParam(name = "vehicle_model", required = true) String vehi_model)
	{
		
		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
		Vehicle thisVehicle=vehicleService.findVehicleByVehicleModelAndCustomer(vehi_model, thisCustomer);
		//Device thisDevice = deviceService.findDeviceByHardwareIdAndCustomer(devHardId, thisCustomer);
		
		if(thisVehicle==null)
		{
			log.error("Vehicle not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		log.info("Returned vehicle details: "+ " [vehicle:" +thisVehicle.getVehicleName()+ "]");
		return new ResponseEntity<>(thisVehicle, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param auth
	 * @param vehicle name
	 * @return
	 */
	
	
	
	@RequestMapping(value = "/customer/get_vehicle_by_name", method = RequestMethod.GET)
	public ResponseEntity<Vehicle> getVehicleByName(Authentication auth,@RequestParam(name = "vehicle_name", required = true) String vehi_name)
	{
		
		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
		Vehicle thisVehicle=vehicleService.findVehicleByName(vehi_name, thisCustomer);
		

	
		if(thisVehicle==null)
		{
			log.error("Vehicle not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		log.info("Returned vehicle details: "+ " [vehicle:" +thisVehicle.getVehicleName()+ "]");
	//	List<ImportReceipt> importReceipts = this.importReceiptRepository.findByImportType(importType, new PageRequest(0, 1, Direction.DESC, "Timestamp"));
		//Set<Vehicle> vehicles=(Set<Vehicle>) this.vehicleService.getAllVehicle();
		return new ResponseEntity<>(thisVehicle, HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @param auth
	 * @param vehicle name
	 * @return
	 */
	
	@RequestMapping(value = "/customer/get_all_vehicle_by_name/", method = RequestMethod.GET)
	public ResponseEntity<List<Vehicle>> getAllVehicleByName(Authentication auth,@RequestParam(name = "vehicle_name", required = true) String vehi_name)
	{
		
		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
		//Vehicle thisVehicle=vehicleService.findVehicleByName(vehi_name, thisCustomer);
		List<Vehicle> thisVehicles=vehicleService.findeAllVehiclesByName(vehi_name,thisCustomer);
		

	
		if(thisVehicles==null)
		{
			log.error("Vehicles not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		//log.info("Returned vehicle details: "+ " [vehicle:" +thisVehicle.getVehicleName()+ "]");
	   //	List<ImportReceipt> importReceipts = this.importReceiptRepository.findByImportType(importType, new PageRequest(0, 1, Direction.DESC, "Timestamp"));
		//Set<Vehicle> vehicles=(Set<Vehicle>) this.vehicleService.getAllVehicle();
		return new ResponseEntity<>(thisVehicles, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param auth
	 * @param vehicle name
	 * @return
	 */
	/*
	@RequestMapping(value = "/customer/get_all_distinct_vehicle_model_by_name/", method = RequestMethod.GET)
	public ResponseEntity<List<Vehicle>> getAllDistinctVehicleByName(Authentication auth,@RequestParam(name = "vehicle_name", required = true) String vehi_name)
	{
		
		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
		//Vehicle thisVehicle=vehicleService.findVehicleByName(vehi_name, thisCustomer);
		//List<Vehicle> thisVehicles=vehicleService.findeAllVehiclesByName(vehi_name,thisCustomer);
		List<Vehicle> thisVehiclemodels=vehicleService.getAllDistinctVehicleModels();

	
		if(thisVehiclemodels==null)
		{
			log.error("Vehicles not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		//log.info("Returned vehicle details: "+ " [vehicle:" +thisVehicle.getVehicleName()+ "]");
	   //	List<ImportReceipt> importReceipts = this.importReceiptRepository.findByImportType(importType, new PageRequest(0, 1, Direction.DESC, "Timestamp"));
		//Set<Vehicle> vehicles=(Set<Vehicle>) this.vehicleService.getAllVehicle();
		return new ResponseEntity<>(thisVehiclemodels, HttpStatus.OK);
	}
	
	*/
	
	/**
	 * 
	 * @param auth
	 * @param vehicle model
	 * @return
	 */
	
	@RequestMapping(value = "/customer/get_all_vehicle_by_model", method = RequestMethod.GET)
	public ResponseEntity<List<Vehicle>> getAllVehicleByModel(Authentication auth,@RequestParam(name = "vehicle_model", required = true) String vehi_model)
	{
		
		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
		//Vehicle thisVehicle=vehicleService.findVehicleByName(vehi_name, thisCustomer);
		List<Vehicle> thisVehicles=vehicleService.findeAllVehiclesByModel(vehi_model,thisCustomer);
		

	
		if(thisVehicles==null)
		{
			log.error("Vehicles not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		//log.info("Returned vehicle details: "+ " [vehicle:" +thisVehicle.getVehicleName()+ "]");
	   //	List<ImportReceipt> importReceipts = this.importReceiptRepository.findByImportType(importType, new PageRequest(0, 1, Direction.DESC, "Timestamp"));
		//Set<Vehicle> vehicles=(Set<Vehicle>) this.vehicleService.getAllVehicle();
		return new ResponseEntity<>(thisVehicles, HttpStatus.OK);
	}
	

	/**
	 * 
	 * @param auth
	 * @param vehicle model
	 * @return
	 */
	/*
	@RequestMapping(value = "/customer/get_all_vehicle_by_dates", method = RequestMethod.GET)
	public ResponseEntity<List<Vehicle>> getAllVehicleByDates(Authentication auth,@RequestParam(name = "vehicle_start_date", required = true) String vehi_start,@RequestParam(name = "vehicle_end_date", required = true) String vehi_end)
	{
		
		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
		//Vehicle thisVehicle=vehicleService.findVehicleByName(vehi_name, thisCustomer);
		
	//	List<Vehicle> thisVehicles=vehicleService.findeAllVehiclesByModel(vehi_model,thisCustomer);
		List<Vehicle> thisVehicles=vehicleService.findeAllVehiclesByDates(vehi_start, vehi_end,thisCustomer);
	//	List<Vehicle> thisVehicles=vehicleService.findeAllVehiclesByDates(vehi_start, vehi_end, thisCustomer);

	
		if(thisVehicles==null)
		{
			log.error("Vehicles not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		//log.info("Returned vehicle details: "+ " [vehicle:" +thisVehicle.getVehicleName()+ "]");
	   //	List<ImportReceipt> importReceipts = this.importReceiptRepository.findByImportType(importType, new PageRequest(0, 1, Direction.DESC, "Timestamp"));
		//Set<Vehicle> vehicles=(Set<Vehicle>) this.vehicleService.getAllVehicle();
		return new ResponseEntity<>(thisVehicles, HttpStatus.OK);
	}
	
	*/
	
	
	
	
	/**
	 * 
	 * @param vehicle model
	 * @param auth
	 * @return
	 */
	
	@RequestMapping(value="customer/delete_vehicle", method = RequestMethod.DELETE)
	public ResponseEntity<Vehicle> deleteVehicle(@RequestParam(name = "vehicle_model", required = true) String vehi_model, Authentication auth) {
		
		
		try {
		log.info("Fetching & Deleting Vehicles");
		 Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
		 Vehicle thisVehicle=vehicleService.findVehicleByVehicleModelAndCustomer(vehi_model, thisCustomer);
		 //Device thisDevice = deviceService.findDeviceByHardwareIdAndCustomer(hardwareId, thisCustomer);
		 if (thisVehicle == null) {
			 	log.error("Vehicle not found!!!");
	            return new ResponseEntity<Vehicle>(HttpStatus.NOT_FOUND);
	        }
		 
		 else
		 {
			vehicleService.deleteByVehicleModelAndCustomer(vehi_model, thisCustomer);
			log.info("Vehicle deleted!!! " + "[vehicle name:" +thisVehicle+ "]" );
			return ResponseEntity.ok().build();
		 }
	    }
	catch(Exception ex)
		{
		log.error(ex);
		return new ResponseEntity<Vehicle>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
