package com.javasampleapproach.mysql.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.mysql.model.Device;
import com.javasampleapproach.mysql.model.Peripheral;
import com.javasampleapproach.mysql.model.PeripheralRequest;

@Repository
public interface PeripheralRequestRepository extends JpaRepository<PeripheralRequest, String>  {


	PeripheralRequest findByPeripheral(Peripheral peripheral);
	
	Collection<PeripheralRequest> findByDevice(Device device);
	
	
	
}
