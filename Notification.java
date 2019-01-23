package com.javasampleapproach.mysql.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="notification")
public class Notification implements Serializable{
	
	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "notification_id")
	private long notificationId;
	
	@Column(name = "notification_type")
	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;
	
	@Column(name = "discription")
	private String discription;
	
	@CreatedDate
	@Column(name = "creationDate")
	private Date creationDate;

	@LastModifiedDate
	@Column(name = "notificationTime")
	private Date notificationTime;
	
	@ManyToOne
	@JoinColumn(name = "peripheral_id")
	private Peripheral peripheral;

	@ManyToOne
	@JoinColumn(name = "deviceId")
	@JsonIgnore
	private Device device;
	
	public Notification() {
	}

	public enum NotificationType {
		FaceRecognition, VoiceRecognition, NumberPlateRecognition, MotionDetection, BatteryLevel
	}

	public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@PrePersist
	void creationDate() {
		this.creationDate = this.notificationTime = new Date();
	}
	
	public Date getNotificationTime() {
		return notificationTime;
	}

	public void setNotificationTime(Date notificationTime) {
		this.notificationTime = notificationTime;
	}

	@PreUpdate
	void notificationTime() {
		this.notificationTime = new Date();
	}
	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Peripheral getPeripheral() {
		return peripheral;
	}

	public void setPeripheral(Peripheral peripheral) {
		this.peripheral = peripheral;
	}
	
	
	@Override
	public String toString() {
		return String.format("Notification[notificationId='%i',notificationType='%t',discription='%d' creationDate='%c',notificationTime='%t']", notificationId,
				notificationType, discription, creationDate, notificationTime);
	}
	

}


