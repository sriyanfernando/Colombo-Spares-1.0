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
import com.javasampleapproach.mysql.model.Notification;
import com.javasampleapproach.mysql.model.Peripheral;
import com.javasampleapproach.mysql.model.PeripheralRequest;
import com.javasampleapproach.mysql.service.CustomerService;
import com.javasampleapproach.mysql.service.DeviceService;
import com.javasampleapproach.mysql.service.NotificationService;
import com.javasampleapproach.mysql.service.PeripheralService;

@Controller
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired 
	private PeripheralService peripheralService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private CustomerService customerService;
	
	Logger log = Logger.getLogger(NotificationController.class.getName());
	
	
	@RequestMapping(value = "/customer/add_notification", method = RequestMethod.POST)
	public ResponseEntity<Notification> addNotification(@RequestBody Notification notification,
			@RequestParam(name="dev_hard_id", required=true) String devHardId,
			@RequestParam(name="per_hard_id", required=true) String perHardId, Authentication auth) {

		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
		Device thisDevice = deviceService.findDeviceByHardwareIdAndCustomer(devHardId, thisCustomer);
		Peripheral thisPeripheral = peripheralService.findByHardwareIdAndDevice(perHardId, thisDevice);
		
		

		if (thisDevice == null) {
			log.error("Device not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (thisPeripheral == null) {
			log.error("Peripheral not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Notification newNotification = new Notification();

		newNotification.setDevice(thisDevice);
		newNotification.setPeripheral(thisPeripheral);
		newNotification.setDiscription(notification.getDiscription());
		newNotification.setNotificationType(notification.getNotificationType());
		notificationService.saveNotification(newNotification);
			
		return new ResponseEntity<>(newNotification, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/customer/notification_polling_worker", method = RequestMethod.GET)
	public ResponseEntity<Collection<Notification>> getDeviceNotifications(Authentication auth,
			@RequestParam(name="dev_hard_id",required=true) String devHardId) {

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

		log.info("Returned Notification list: " + "[device name:" + thisDevice.getDeviceName() + "]");
		return new ResponseEntity<>(thisDevice.getNotification(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/customer/peripheral_notification", method = RequestMethod.GET)
	public ResponseEntity<Collection<Notification>> getPeripheralNotifications(Authentication auth, @RequestParam(name="dev_hard_id", required=true) String devHardId, 
			@RequestParam(name="per_hard_id", required=true) String perHardId){
		Customer thisCustomer = customerService.findCustomerByEmail(auth.getName());
		if(thisCustomer == null) {
			log.error("Customer not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Device thisDevice = deviceService.findDeviceByHardwareIdAndCustomer(devHardId, thisCustomer);

		if (thisDevice == null) {
			log.error("Device not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Peripheral thisPeripheral = peripheralService.findByHardwareIdAndDevice(perHardId, thisDevice);
		
		if (thisPeripheral==null)
		{
			log.error("Peripheral not found!!!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		log.info("Returned Notification list: " + "[peripheral name:" +thisPeripheral.getPeripheralName() + "]");
		return new ResponseEntity<>(thisPeripheral.getNotification(), HttpStatus.OK);
		
		
	}
	
	
	@RequestMapping(value = "/customer/delete_notification", method=RequestMethod.DELETE)
	public ResponseEntity<Notification> deleteNotification(Authentication auth, @RequestParam(name="dev_hard_id", required=true) String devHardId, 
			@RequestParam(name="per_hard_id", required=true) String perHardwareId, @RequestParam(name="notification_id", required=true) long notificationId ){
		
		Notification notification = notificationService.findNotificationById(notificationId);
		
		if (notification==null)
		{
			log.error("Notification Doesn't Exists");
			return new ResponseEntity<Notification>(HttpStatus.NOT_FOUND);
		}
		else
		{
		
		try {
			log.info("Fetching & Deleting Device");
			
				notificationService.deleteByNotificationId(notificationId);
				return ResponseEntity.ok().build();
			 
		    }
		catch(Exception ex)
			{
			log.error(ex);
			return new ResponseEntity<Notification>(HttpStatus.BAD_REQUEST);
			}
		}
		
		
	}

}
