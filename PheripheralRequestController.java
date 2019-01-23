package com.javasampleapproach.mysql.controller;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javasampleapproach.mysql.model.Customer;
import com.javasampleapproach.mysql.model.Device;
import com.javasampleapproach.mysql.model.Peripheral;
import com.javasampleapproach.mysql.model.PeripheralRequest;
import com.javasampleapproach.mysql.service.CustomerService;
import com.javasampleapproach.mysql.service.DeviceService;
import com.javasampleapproach.mysql.service.PeripheralRequestService;
import com.javasampleapproach.mysql.service.PeripheralService;

@Controller
public class PheripheralRequestController {

	@Autowired
	private PeripheralRequestService peripheralRequestService;

	@Autowired
	private PeripheralService peripheralService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private CustomerService customerService;

	static Logger log = Logger.getLogger(DeviceController.class.getName());

	@RequestMapping(value = "/customer/peripheral_request", method = RequestMethod.POST)
	public ResponseEntity<PeripheralRequest> addPeripheralRequest(@RequestBody PeripheralRequest peripheralRequest,
			@RequestParam(name="dev_hard_id", required=true) String deviceHardwareId,
			@RequestParam(name="per_hard_id", required=true) String peripheralHardwareId, Authentication auth) {

		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
		Device thisDevice = deviceService.findDeviceByHardwareIdAndCustomer(deviceHardwareId, thisCustomer);
		Peripheral thisPeripheral = peripheralService.findByHardwareIdAndDevice(peripheralHardwareId, thisDevice);
		PeripheralRequest existingPeripheralRequest = peripheralRequestService
				.findPeripheralRequestByPeripheral(thisPeripheral);

		if (thisDevice == null) {
			log.error("Device not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (thisPeripheral == null) {
			log.error("Peripheral not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (existingPeripheralRequest == null)

		{
			PeripheralRequest newPeripheralRequest = new PeripheralRequest();

			newPeripheralRequest.setRequestType(peripheralRequest.getRequestType());
			newPeripheralRequest.setPeripheral(thisPeripheral);
			newPeripheralRequest.setDevice(thisDevice);
			newPeripheralRequest = peripheralRequestService.savePeripheralRequest(newPeripheralRequest);

			log.info("Added peripheral request device: " + "[hardware id:" + deviceHardwareId + "]");
			return new ResponseEntity<>(HttpStatus.CREATED);
		}

		else
			existingPeripheralRequest.setRequestType(peripheralRequest.getRequestType());
		existingPeripheralRequest = peripheralRequestService.savePeripheralRequest(existingPeripheralRequest);
		log.info("Updated!!!");
		return new ResponseEntity<>(existingPeripheralRequest, HttpStatus.OK);
	}
	
	

	

	@RequestMapping(value = "/customer/peripheral_request/polling_worker", method = RequestMethod.GET)
	public ResponseEntity<PeripheralRequest> getPeripheralRequestPolling(Authentication auth,
			@RequestParam(name="dev_hard_id",required=true) String devHardId, @RequestParam(name="per_hard_id",required=true) String perHardId) {

		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());

		Device thisDevice = deviceService.findDeviceByHardwareIdAndCustomer(devHardId, thisCustomer);

		Peripheral thisPeripheral = peripheralService.findByHardwareIdAndDevice(perHardId, thisDevice);
		PeripheralRequest thisPeripheralRequest = peripheralRequestService
				.findPeripheralRequestByPeripheral(thisPeripheral);

		log.info("Returned");
		return new ResponseEntity<>(thisPeripheralRequest, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/customer/device_polling_worker", method = RequestMethod.GET)
	public ResponseEntity<Collection<PeripheralRequest>> getPeripheralRequests(Authentication auth,
			@RequestParam(name="dev_hard_id",required=true) String devHardwareId) {

		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());

		if (thisCustomer == null) {
			log.error("Customer not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Device thisDevice = deviceService.findDeviceByHardwareIdAndCustomer(devHardwareId, thisCustomer);

		if (thisDevice == null) {
			//log.error("Device not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		log.info("Returned periperalRequest list: " + "[device name:" + thisDevice.getDeviceName() + "]");
		return new ResponseEntity<>(thisDevice.getPeripheralRequest(), HttpStatus.OK);

	}

}
