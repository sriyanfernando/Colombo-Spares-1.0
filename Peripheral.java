package com.javasampleapproach.mysql.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Peripherals")
public class Peripheral implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "peripheralId")
	private long peripheralId;

	@Column(name = "per_hardware_id")
	@NotNull
	private String perHardwareId;

	@Column(name = "peripheral_name")
	private String peripheralName;

	@Column(name = "peripheral_type")
	@Enumerated(EnumType.STRING)
	private PheripheralType peripheralType;

	@Column(name = "is_active")
	private Boolean isActive;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deviceId")
	private Device device;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "peripheral")
	private Set<Notification> notification;

	public enum PheripheralType {
		Audio, Video, AudioAndVideo
	}

	public Peripheral() {
	}

	public Peripheral(String name) {
		this.peripheralName = name;
	}

	public long getPeripheralId() {
		return peripheralId;
	}

	public void setPeripheralId(long peripheralId) {
		this.peripheralId = peripheralId;
	}

	public String getperHardwareId() {
		return perHardwareId;
	}

	public void setperHardwareId(String perHardwareId) {
		this.perHardwareId = perHardwareId;
	}

	public String getPeripheralName() {
		return peripheralName;
	}

	public void setPeripheralName(String peripheralName) {
		this.peripheralName = peripheralName;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public PheripheralType getPeripheralType() {
		return peripheralType;
	}

	public void setPeripheralType(PheripheralType peripheralType) {
		this.peripheralType = peripheralType;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	

	public Set<Notification> getNotification() {
		return notification;
	}

	public void setNotification(Set<Notification> notification) {
		this.notification = notification;
	}

	@Override
	public String toString() {
		return String.format(
				"Peripheral[peripheralId='%i',perHardwareId='%d', peripheralName='%s' , peripheralType='%s']",
				peripheralId, perHardwareId, peripheralName, peripheralType);
	}

}
