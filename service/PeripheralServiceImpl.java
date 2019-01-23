package com.javasampleapproach.mysql.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasampleapproach.mysql.model.Device;
import com.javasampleapproach.mysql.model.Peripheral;
import com.javasampleapproach.mysql.repository.PeripheralRepository;

@Service
public class PeripheralServiceImpl implements PeripheralService{
	
	
	@Autowired
	private PeripheralRepository peripheralRepository;

	@Override
	public List<Peripheral> getAllPeripheral() {
		return peripheralRepository.findAll();
	}

	@Override
	public Peripheral savePeripheral(Peripheral peripheral) {
		return peripheralRepository.save(peripheral);
	}

	@Override
	public Peripheral findPeripheral(String perHardwareId) {
		return (Peripheral) peripheralRepository.findByPerHardwareId(perHardwareId);
	}

	@Override
	public Peripheral findByHardwareIdAndDevice(String perHardwareId, Device device) {
		return peripheralRepository.findByPerHardwareIdAndDevice(perHardwareId, device);
	}

}
