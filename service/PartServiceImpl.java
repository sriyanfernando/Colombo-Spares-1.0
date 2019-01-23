package com.javasampleapproach.mysql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasampleapproach.mysql.model.Customer;
import com.javasampleapproach.mysql.model.Part;
import com.javasampleapproach.mysql.model.Vehicle;
import com.javasampleapproach.mysql.repository.PartRepository;


@Service
public class PartServiceImpl implements PartService{

	@Autowired
	private PartRepository partRepository;
	@Override
	public Part savePart(Part part) {
		
		return partRepository.save(part);
	}
	@Override
	public Part findByPartYearAndVehicle(String partYear, Vehicle vehicle) {
	
		return partRepository.findByPartYearAndVehicle(partYear, vehicle);
	}
	@Override
	public Part findPartById(Long id, Vehicle vehicle) {
		
		return partRepository.findByPartIdAndVehicle(id, vehicle);
	}
	
	

	
}
