package com.javasampleapproach.mysql.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.javasampleapproach.mysql.model.Customer;
import com.javasampleapproach.mysql.model.Vehicle;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

    Vehicle findByVehicleId(Long vehicleId);
    Vehicle findByVehicleType(String vehicleType);
    
    //@Query("SELECT DISTINCT a.city FROM Address a")
   // List<String> findDistinctCity();
    
    
    @Query("SELECT DISTINCT v.vehicleName FROM Vehicle v")
    List<Vehicle> findDistinctvehicleNames();
    
   // @Query("SELECT DISTINCT v.vehicleModel FROM Vehicle v")
   // List<Vehicle> findDistinctvehicleModels();
    
     
	Vehicle findByVehicleNameAndCustomer(String vehicleName, Customer customer);
	Vehicle findByVehicleModelAndCustomer(String vehicleModel, Customer customer);
	Vehicle findByVehicleIdAndCustomer(Long vehicleId,Customer customer);
	
	Vehicle findByVehicleTypeAndCustomer(String vehicleType, Customer customer);
	Vehicle findByVehicleYearAndCustomer(String vehicleYear, Customer customer);
	
	List<Vehicle> findAllByVehicleNameAndCustomer(String vehicleName,Customer customer);
	List<Vehicle> findAllByVehicleModelAndCustomer(String vehicleModel,Customer customer);


}
