package com.javasampleapproach.mysql.service;

import java.util.List;

import com.javasampleapproach.mysql.model.Device;
import com.javasampleapproach.mysql.model.Peripheral;

public interface PeripheralService {
	
	List<Peripheral> getAllPeripheral();
	public Peripheral savePeripheral(Peripheral peripheral);
	public Peripheral findPeripheral (String peripheralId);
	public Peripheral findByHardwareIdAndDevice(String perHardwareId,Device device);
	
}