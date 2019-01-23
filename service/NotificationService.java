package com.javasampleapproach.mysql.service;

import java.util.Collection;
import java.util.List;

import com.javasampleapproach.mysql.model.Device;
import com.javasampleapproach.mysql.model.Notification;
import com.javasampleapproach.mysql.model.Peripheral;


public interface NotificationService {
	
	public Notification saveNotification(Notification notification);
	List<Notification> getAllNotification();
	
	public Notification findNotificationById(long notificationId);
	
	Collection<Notification> findNotificationByDevice(Device device);
	Collection<Notification> findNotificationByPeripheral(Peripheral peripheral);
	void deleteByNotificationId(long notificationId);

}
