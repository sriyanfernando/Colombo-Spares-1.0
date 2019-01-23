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
import com.javasampleapproach.mysql.service.CustomerService;
import com.javasampleapproach.mysql.service.DeviceService;
import com.javasampleapproach.mysql.service.PeripheralService;

@Controller
public class PeripheralController {

	@Autowired
	private PeripheralService peripheralService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private DeviceService deviceService;

	static Logger log = Logger.getLogger(DeviceController.class.getName());

	/**
	 * 
	 * @param peripheral
	 * @param hardwareId
	 * @param auth
	 * @return
	 */

	@RequestMapping(value = "/customer/add_peripheral/", method = RequestMethod.POST)
	public ResponseEntity<Peripheral> addPeripheral(@RequestBody Peripheral peripheral,
			@RequestParam(name = "dev_hard_id", required = true) String hardwareId, Authentication auth) {

		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
		Device thisDevice = deviceService.findDeviceByHardwareIdAndCustomer(hardwareId, thisCustomer);

		if (thisDevice == null) {
			log.error("Device not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Peripheral existingPeripheral = peripheralService.findByHardwareIdAndDevice(peripheral.getperHardwareId(),
				thisDevice);

		if (existingPeripheral != null) {
			log.error("Peripheral Already Exists!!!");
			return new ResponseEntity<Peripheral>(existingPeripheral, HttpStatus.FOUND);
		}

		else {
			Peripheral newPeripheral = new Peripheral();
			
			
			
			/*try {
				
				String encodedHardwareId = peripheral.getperHardwareId();
				
		        byte[] base64decodedBytes = Base64.getDecoder().decode(encodedHardwareId); 
		        String originalHardwareId = new String(base64decodedBytes, "utf-8");
				newPeripheral.setperHardwareId(originalHardwareId );
		      	} catch(UnsupportedEncodingException e) {
		      		log.error("Error :" + e.getMessage());
		      }*/
			
			
			newPeripheral.setIsActive(true);

			newPeripheral.setPeripheralName(peripheral.getPeripheralName());
			newPeripheral.setperHardwareId(peripheral.getperHardwareId());
			newPeripheral.setPeripheralType(peripheral.getPeripheralType());
			newPeripheral.setDevice(thisDevice);
			newPeripheral = peripheralService.savePeripheral(newPeripheral);

			log.info("Added peripheral device: " + "[hardware id:" + peripheral.getperHardwareId() + "]");
			return new ResponseEntity<Peripheral>(HttpStatus.CREATED);
		}
	}

	/**
	 * 
	 * @param auth
	 * @param hardwareId
	 * @return
	 */

	@RequestMapping(value = "/customer/get_peripherals", method = RequestMethod.GET)
	public ResponseEntity<Collection<Peripheral>> getPeripherals(Authentication auth,
			@RequestParam(name = "dev_hard_id", required = true) String hardwareId) {

		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());

		if (thisCustomer == null) {
			log.error("Customer not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Device thisDevice = deviceService.findDeviceByHardwareIdAndCustomer(hardwareId, thisCustomer);

		if (thisDevice == null) {
			log.error("Device not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		log.info("Returned periperal list: " + "[device name:" + thisDevice.getDeviceName() + "]");
		return new ResponseEntity<>(thisDevice.getPeripherals(), HttpStatus.OK);

	}

	/**
	 * 
	 * @param auth
	 * @param devHardId
	 * @param perHardId
	 * @return
	 */

	@RequestMapping(value = "/customer/get_peripheral", method = RequestMethod.GET)
	public ResponseEntity<Peripheral> getPeripheral(Authentication auth, @RequestParam(name = "dev_hard_id", required = true) String devHardId,
			@RequestParam(name = "per_hard_id", required = true) String perHardId) {
		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());

		if (thisCustomer == null) {
			log.error("Customer not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		
		

		Device thisDevice = deviceService.findDeviceByHardwareIdAndCustomer(devHardId, thisCustomer);

		if (thisDevice == null) {
			log.error("Device not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Peripheral thisPeripheral = peripheralService.findByHardwareIdAndDevice(perHardId, thisDevice);

		if (thisPeripheral == null) {
			log.error("Peripheral device not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		log.info("Returned periperal details: " + "[device name:" + thisDevice.getDeviceName() + "]"
				+ "  [peripheral name:" + thisPeripheral.getPeripheralName() + "]");
		return new ResponseEntity<>(thisPeripheral, HttpStatus.OK);
	}

	/**
	 * 
	 * @param peripheral
	 * @param auth
	 * @param devHardId
	 * @param perHardId
	 * @return
	 */
	@RequestMapping(value = "/customer/update_peripheral", method = RequestMethod.PUT)
	public ResponseEntity<Peripheral> updatePeripheral(@RequestBody Peripheral peripheral, Authentication auth,
			@RequestParam(name = "dev_hard_id", required = true) String devHardId, @RequestParam(name = "per_hard_id", required = true) String perHardId) {
		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());

		if (thisCustomer == null) {
			log.error("Customer not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Device thisDevice = deviceService.findDeviceByHardwareIdAndCustomer(devHardId, thisCustomer);

		if (thisDevice == null) {
			log.error("Device not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Peripheral thisPeripheral = peripheralService.findByHardwareIdAndDevice(perHardId, thisDevice);

		if (peripheral.getIsActive() == null || peripheral.getPeripheralType() == null
				|| peripheral.getPeripheralName() == null || peripheral.getPeripheralName().trim() == "") {
			log.error("Error here!!!");
			return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
		}
		thisPeripheral.setIsActive(peripheral.getIsActive());
		thisPeripheral.setPeripheralName(peripheral.getPeripheralName());
		thisPeripheral.setPeripheralType(peripheral.getPeripheralType());

		thisPeripheral = peripheralService.savePeripheral(thisPeripheral);

		return new ResponseEntity<>(thisPeripheral, (HttpStatus.ACCEPTED));
	}

}
