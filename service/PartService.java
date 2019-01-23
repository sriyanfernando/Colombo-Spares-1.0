package com.javasampleapproach.mysql.service;

import com.javasampleapproach.mysql.model.Customer;
import com.javasampleapproach.mysql.model.Device;
import com.javasampleapproach.mysql.model.Part;
import com.javasampleapproach.mysql.model.Peripheral;
import com.javasampleapproach.mysql.model.Vehicle;


public interface PartService 
{
	public Part savePart(Part part);
	public Part findByPartYearAndVehicle(String partYear, Vehicle vehicle);
		
	 public Part findPartById(Long id,Vehicle vehicle);
}
