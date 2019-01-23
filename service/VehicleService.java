package com.javasampleapproach.mysql.service;

import java.util.List;

import com.javasampleapproach.mysql.model.Customer;
import com.javasampleapproach.mysql.model.Vehicle;;

public interface VehicleService
{

    public Vehicle findVehicleByName(String name, Customer customer);	
    public Vehicle findVehicleById(Long id,Customer customer);
    public Vehicle findVehicleByType(String type,Customer customer);
    
    
    List<Vehicle> getAllDistinctVehicle();
   // List<Vehicle> getAllDistinctVehicleModels();
    
	public Vehicle findVehicleByVehicleModelAndCustomer(String vehicleModel, Customer customer);	
	public Vehicle saveVehicle(Vehicle vehicle);
	List<Vehicle> getAllVehicle();
	void deleteByNameAndCustomer(String vehicleName, Customer customer);
	void deleteByVehicleModelAndCustomer(String vehicleModel, Customer customer);
	
	
	public List<Vehicle> findeAllVehiclesByName(String vehicleName,Customer customer);
	public List<Vehicle> findeAllVehiclesByModel(String vehicleModel,Customer customer);
	
	

	public Vehicle findVehicleByVehicleTypeAndCustomer(String vehicleType, Customer customer);	
	public Vehicle findVehicleByVehicleYearAndCustomer(String vehicleYear,Customer customer);       
	

}
