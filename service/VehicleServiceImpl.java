package com.javasampleapproach.mysql.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasampleapproach.mysql.model.Customer;
import com.javasampleapproach.mysql.model.Vehicle;
import com.javasampleapproach.mysql.repository.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService {
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Override
	public Vehicle findVehicleByName(String name, Customer customer)
	{	  
		return vehicleRepository.findByVehicleNameAndCustomer(name, customer);	    	
	}

	
	@Override
	public Vehicle findVehicleById(Long id, Customer customer) {
	
		return vehicleRepository.findByVehicleIdAndCustomer(id, customer);
	}
	
	@Override
	public Vehicle findVehicleByType(String type, Customer customer) {

		return vehicleRepository.findByVehicleTypeAndCustomer(type, customer);
	}

	
	@Override
	public Vehicle findVehicleByVehicleModelAndCustomer(String vehicleModel, Customer customer)
	{	
		return vehicleRepository.findByVehicleModelAndCustomer(vehicleModel, customer);
	}
	
	@Override
	public Vehicle findVehicleByVehicleTypeAndCustomer(String vehicleType,Customer customer) {
		
		return vehicleRepository.findByVehicleTypeAndCustomer(vehicleType, customer);
	}
	
	@Override
	public Vehicle findVehicleByVehicleYearAndCustomer(String vehicleYear,Customer customer) {
		  return vehicleRepository.findByVehicleYearAndCustomer(vehicleYear, customer);
		
	}

	@Override
	public Vehicle saveVehicle(Vehicle vehicle) {
	
		return vehicleRepository.save(vehicle);
	}

	@Override
	public List<Vehicle> getAllVehicle() {
		
		return vehicleRepository.findAll();
	}


	@Override
	public List<Vehicle> getAllDistinctVehicle() {
	
		return vehicleRepository.findDistinctvehicleNames();
	}
	
	//@Override
	//public List<Vehicle> getAllDistinctVehicleModels() {
		
	//	return vehicleRepository.findDistinctvehicleModels();
//	}


	
	@Override
	public void deleteByNameAndCustomer(String vehicleName, Customer customer) {
		Vehicle entity=vehicleRepository.findByVehicleNameAndCustomer(vehicleName, customer);
		vehicleRepository.delete(entity);
		
	}

	@Override
	public void deleteByVehicleModelAndCustomer(String vehicleModel, Customer customer) {
		Vehicle entity=vehicleRepository.findByVehicleModelAndCustomer(vehicleModel, customer);
		vehicleRepository.delete(entity);
		
	}

	@Override
	public List<Vehicle> findeAllVehiclesByName(String vehicleName,Customer customer) {
		
		return vehicleRepository.findAllByVehicleNameAndCustomer(vehicleName,customer);
	}

	@Override
	public List<Vehicle> findeAllVehiclesByModel(String vehicleModel,Customer customer) {
		
		return vehicleRepository.findAllByVehicleModelAndCustomer(vehicleModel,customer);
	}








	





	
	
	
}
