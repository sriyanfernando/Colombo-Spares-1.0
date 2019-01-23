package com.javasampleapproach.mysql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.mysql.model.Device;
import com.javasampleapproach.mysql.model.Peripheral;

@Repository
public interface PeripheralRepository extends JpaRepository<Peripheral, String> {
	
	List<Peripheral> findByPerHardwareId(String perHardwareId);
	Peripheral findByPerHardwareIdAndDevice(String perHardwareId, Device device);
	
}