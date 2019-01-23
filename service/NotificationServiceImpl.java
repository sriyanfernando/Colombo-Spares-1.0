package com.javasampleapproach.mysql.service;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.javasampleapproach.mysql.model.Device;
import com.javasampleapproach.mysql.model.Notification;
import com.javasampleapproach.mysql.model.Peripheral;
import com.javasampleapproach.mysql.repository.NotificationRepository;


@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class NotificationServiceImpl implements NotificationService {
	
	@PersistenceContext
    protected EntityManager entityManager;
	
	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public Notification saveNotification(Notification notification) {
		return notificationRepository.save(notification);
	}


	@Override
	public List<Notification> getAllNotification() {
		return notificationRepository.findAll();
	}

	@Override
	public Collection<Notification> findNotificationByDevice(Device device) {
		return notificationRepository.findByDevice(device);
	}

	@Override
	public Collection<Notification> findNotificationByPeripheral(Peripheral peripheral) {
		return notificationRepository.findByPeripheral(peripheral);
	}


	@Override
	public void deleteByNotificationId(long notificationId) {
		notificationRepository.deleteByNotificationId(notificationId);
	}


	@Override
	public Notification findNotificationById(long notificationId) {
		return notificationRepository.findByNotificationId(notificationId);
	}

}
