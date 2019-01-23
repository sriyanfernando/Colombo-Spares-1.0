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
import com.javasampleapproach.mysql.model.EntityAlreadyExistsException;
import com.javasampleapproach.mysql.service.CustomerService;
import com.javasampleapproach.mysql.service.DeviceService;

@Controller
public class DeviceController
{
	
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private DeviceService deviceService;
	
	static Logger log = Logger.getLogger(DeviceController.class.getName());
	
	
	/**
	 * 
	 * @param device
	 * @param auth
	 * @return 
	 */
	
	
	
	
	
	@RequestMapping(value = "/customer/add_device", method = RequestMethod.POST)
	public ResponseEntity<Device> addDevice(@RequestBody Device device, Authentication auth) {

		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());

		Device existingDevice = deviceService.findDeviceByHardwareIdAndCustomer(device.getdevHardwareId(), thisCustomer);

		if (existingDevice != null) {
			log.error("Device already exist!!!");
			throw new EntityAlreadyExistsException("device : " + existingDevice.getdevHardwareId());
		} 
		else {
			Device newDevice = new Device();
			newDevice.setDeviceName(device.getDeviceName());
			if(device.getdevHardwareId()==null)
			{
				log.error("HardwareId can't be empty!!!");
				return new ResponseEntity<Device>(HttpStatus.RESET_CONTENT);
			}
			newDevice.setdevHardwareId(device.getdevHardwareId());
			newDevice.setDeviceIp(device.getDeviceIp());
			newDevice.setCustomer(thisCustomer);
			newDevice.setIsActive(true);
			newDevice = deviceService.saveDevice(newDevice);
			log.info("Device name:"+device.getDeviceName()+" added successfully!!!");
			return new ResponseEntity<Device>(HttpStatus.CREATED);
		}
	}
	
	
	

	
	@RequestMapping(value = "/customer/get_device_list", method = RequestMethod.GET)
	public ResponseEntity<Collection<Device>> getDevices(Authentication auth) {
		Customer existingCustomer = customerService.findCustomerByEmail(auth.getName());
		log.info("Returned device list: " + "[customer:" +existingCustomer.getcustomerName() +"]");
		return new ResponseEntity<>(existingCustomer.getDevices(), HttpStatus.OK);
	}
	
	

	
	@RequestMapping(value = "/customer/get_device", method = RequestMethod.GET)
	public ResponseEntity<Device> getDeviceById(Authentication auth,@RequestParam(name = "dev_hard_id", required = true) String devHardId) {
		
		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
		
		Device thisDevice = deviceService.findDeviceByHardwareIdAndCustomer(devHardId, thisCustomer);
		
		if(thisDevice==null)
		{
			log.error("Device not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		log.info("Returned device details: "+ " [device:" +thisDevice.getDeviceName()+ "]");
		return new ResponseEntity<>(thisDevice, HttpStatus.OK);
	}
	
	

	
	@RequestMapping(value = "/customer/deactivate_device" , method = RequestMethod.PUT)
	public ResponseEntity<Device> deactivateDevice(Authentication auth,@RequestParam(name = "dev_hard_id", required = true) String hardwareId)
	{
		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
		Device thisDevice = deviceService.findDeviceByHardwareIdAndCustomer(hardwareId, thisCustomer);
		 if(thisDevice==null)
		 {
			 log.error("Device not found!!!");
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
		 if(!thisDevice.getIsActive())
		 {
			 log.error("Device is not active!!!");
			 return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		 }
		 thisDevice.setIsActive(false);
		 deviceService.saveDevice(thisDevice);
		 
		 log.info("Deactivated!!! " +"[device name:" +thisDevice.getDeviceName()+ "]" );
		 return new ResponseEntity<> (HttpStatus.OK);
	}
	
	

	
	@RequestMapping(value="customer/delete_device", method = RequestMethod.DELETE)
	public ResponseEntity<Device> deleteDevice(@RequestParam(name = "dev_hard_id", required = true) String hardwareId, Authentication auth) {
		
		
		try {
		log.info("Fetching & Deleting Device");
		 Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
		 Device thisDevice = deviceService.findDeviceByHardwareIdAndCustomer(hardwareId, thisCustomer);
		 if (thisDevice == null) {
			 	log.error("Device not found!!!");
	            return new ResponseEntity<Device>(HttpStatus.NOT_FOUND);
	        }
		 
		 else
		 {
			deviceService.deleteByHardwareIdAndCustomer(hardwareId, thisCustomer);
			log.info("Device deleted!!! " + "[device name:" +thisDevice+ "]" );
			return ResponseEntity.ok().build();
		 }
	    }
	catch(Exception ex)
		{
		log.error(ex);
		return new ResponseEntity<Device>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
}
