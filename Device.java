package com.javasampleapproach.mysql.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "Device")
public class Device implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "device_id")
	private long deviceId;

	@Column(name = "device_name")
	private String deviceName;
	
	@Column (name = "dev_hardware_id")
	@NotNull
	private String devHardwareId;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	@Column (name = "device_ip")
	private String deviceIp;
	
	@CreatedDate
	@Column(name = "creationDate")
	private Date creationDate;

	@LastModifiedDate
	@Column(name = "lastModifiedDate")
	private Date lastModifiedDate;

	@ManyToOne
	@JoinColumn(name = "customerId")
	@JsonIgnore
	private Customer customer;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "device")
	private Set<Peripheral> peripherals;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "device")
	private Set<PeripheralRequest> peripheralRequest;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "device")
	private Set<Notification> notification;
	
	public Set<Peripheral> getPeripherals() {
		return peripherals;
	}

	public void setPeripheral(Set<Peripheral> peripherals) {
		this.peripherals = peripherals;
	}
	
	
	public Set<PeripheralRequest> getPeripheralRequest() {
		return peripheralRequest;
	}

	public void setPeripheralRequest(Set<PeripheralRequest> peripheralRequest) {
		this.peripheralRequest = peripheralRequest;
	}
	

	public Set<Notification> getNotification() {
		return notification;
	}

	public void setNotification(Set<Notification> notification) {
		this.notification = notification;
	}
	

	public Device()
	{
		
	}

	public Device(String name) {
		this.deviceName = name;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}	

	public String getdevHardwareId() {
		return devHardwareId;
	}

	public void setdevHardwareId(String devHardwareId) {
		this.devHardwareId = devHardwareId;
	}
	
	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
	

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = (Timestamp) creationDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer cusotmer) {
		this.customer = cusotmer;
	}
	
	@PrePersist
	void creationDate() {
		this.creationDate = this.lastModifiedDate = new Date();
	}

	@PreUpdate
	void lastModifiedDate() {
		this.lastModifiedDate = new Date();
	}

	@Override
	public String toString() {
		return String.format("Device[deviceId='%d', deviceName='%s', devHardwareId='%h',deviceIp='%I',creationDate='%s', lastModifiedDate='%s']", deviceId, deviceName, devHardwareId, deviceIp,creationDate, lastModifiedDate);
	}
}
