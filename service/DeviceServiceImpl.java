package com.javasampleapproach.mysql.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasampleapproach.mysql.model.Customer;
import com.javasampleapproach.mysql.model.Device;
import com.javasampleapproach.mysql.repository.DeviceRepository;

@Service
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceRepository deviceRepository;

	@Override
	public Device saveDevice(Device device) {
		return deviceRepository.save(device);

	}

	@Override
	public Device findDeviceByName(String name, Customer customer) {
		return deviceRepository.findByDeviceNameAndCustomer(name, customer);
	}

	@Override
	public List<Device> getAllDevice() {
		return deviceRepository.findAll();
	}

	@Override
	public void deleteByNameAndCustomer(String deviceName, Customer customer) {
		Device entity = deviceRepository.findByDeviceNameAndCustomer(deviceName, customer);
		deviceRepository.delete(entity);
		
	}

	@Override
	public Device findDeviceByHardwareIdAndCustomer(String devHardwareId, Customer customer) {
		return deviceRepository.findByDevHardwareIdAndCustomer(devHardwareId, customer);
	}

	@Override
	public void deleteByHardwareIdAndCustomer(String devHardwareId, Customer customer) {
		Device entity = deviceRepository.findByDevHardwareIdAndCustomer(devHardwareId, customer);
		deviceRepository.delete(entity);
		
	}

}
