package com.javasampleapproach.mysql.service;

import java.util.Collection;
import java.util.List;

import com.javasampleapproach.mysql.model.Device;
import com.javasampleapproach.mysql.model.Peripheral;
import com.javasampleapproach.mysql.model.PeripheralRequest;

public interface PeripheralRequestService {
	
	public PeripheralRequest savePeripheralRequest(PeripheralRequest peripheralRequest);
	public PeripheralRequest findPeripheralRequestByPeripheral(Peripheral peripheral);
	List<PeripheralRequest> getAllPeripheralRequest();
	
	Collection<PeripheralRequest> getPeripheralRequest(Device device);
	
}


