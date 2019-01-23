package com.javasampleapproach.mysql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.mysql.model.Customer;
import com.javasampleapproach.mysql.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
	
	List<Device> findByDeviceId(Long deviceId);

	Device findByDeviceNameAndCustomer(String deviceName, Customer customer);
	Device findByDevHardwareIdAndCustomer(String devHardwareId, Customer customer);
	Device findByDeviceIdAndCustomer(String deviceId,Customer customer);
	

}