package com.javasampleapproach.mysql.service;

import java.util.List;

import com.javasampleapproach.mysql.model.Customer;
import com.javasampleapproach.mysql.model.Device;

public interface DeviceService {
	
	public Device findDeviceByName(String name, Customer customer);
	
	public Device findDeviceByHardwareIdAndCustomer(String devHardwareId, Customer customer);

	// public Boolean findDeviceByName (String name, String email);
	public Device saveDevice(Device device);
	List<Device> getAllDevice();
	void deleteByNameAndCustomer(String deviceName, Customer customer);
	void deleteByHardwareIdAndCustomer(String devHardwareId, Customer customer);
	
	

}