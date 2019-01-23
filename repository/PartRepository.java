package com.javasampleapproach.mysql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.mysql.model.Customer;
import com.javasampleapproach.mysql.model.Part;
import com.javasampleapproach.mysql.model.Vehicle;



@Repository
public interface PartRepository extends JpaRepository<Part,Long> 
{

	Part findByPartIdAndVehicle(Long partId,Vehicle vehicle);
	
	Part findByPartYearAndVehicle(String partYear,Vehicle vehicle);
	
}
