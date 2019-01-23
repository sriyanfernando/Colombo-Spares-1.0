package com.javasampleapproach.mysql.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasampleapproach.mysql.model.Device;
import com.javasampleapproach.mysql.model.Peripheral;
import com.javasampleapproach.mysql.model.PeripheralRequest;
import com.javasampleapproach.mysql.repository.PeripheralRequestRepository;

@Service
public class PeripheralRequestServiceImpl implements PeripheralRequestService {

	@Autowired
	private PeripheralRequestRepository peripheralRequestRepository;

	@Override
	public PeripheralRequest savePeripheralRequest(PeripheralRequest peripheralRequest) {

		return peripheralRequestRepository.save(peripheralRequest);
	}

	@Override
	public PeripheralRequest findPeripheralRequestByPeripheral(Peripheral peripheral) {
		return peripheralRequestRepository.findByPeripheral(peripheral);
	}

	@Override
	public List<PeripheralRequest> getAllPeripheralRequest() {
		return peripheralRequestRepository.findAll();
	}

	@Override
	public Collection<PeripheralRequest> getPeripheralRequest(Device device) {
		return peripheralRequestRepository.findByDevice(device);
	}

}
