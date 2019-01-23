package com.javasampleapproach.mysql.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.mysql.model.Device;
import com.javasampleapproach.mysql.model.Notification;
import com.javasampleapproach.mysql.model.Peripheral;


@Repository
public interface NotificationRepository extends JpaRepository<Notification,String> {
	 Collection <Notification> findByPeripheral(Peripheral peripheral);
	 Collection<Notification> findByDevice(Device device);
	 void deleteByNotificationId(long id);
	 Notification findByNotificationId(long id);
	 

}
