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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PeripheralRequest")
public class PeripheralRequest implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "request_id")
	private long requestId;

	@Column(name = "request_type")
	@Enumerated(EnumType.STRING)
	private RequestType requestType;

	@CreatedDate
	@Column(name = "creationDate")
	private Date creationDate;

	@LastModifiedDate
	@Column(name = "lastModifiedDate")
	private Date lastModifiedDate;

	@OneToOne
	@JoinColumn(name = "peripheral_id")
	private Peripheral peripheral;

	@ManyToOne
	@JoinColumn(name = "deviceId")
	@JsonIgnore
	private Device device;

	public PeripheralRequest() {
	}

	public enum RequestType {
		Start, Stop
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public Peripheral getPeripheral() {
		return peripheral;
	}

	public void setPeripheral(Peripheral peripheral) {
		this.peripheral = peripheral;

	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	@PrePersist
	void creationDate() {
		this.creationDate = this.lastModifiedDate = new Date();
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@PreUpdate
	void lastModifiedDate() {
		this.lastModifiedDate = new Date();
	}

	@Override
	public String toString() {
		return String.format("PeripheralRequest[requestId='%i',requestType='%d', lastModifiedDate='%s']", requestId,
				requestType, lastModifiedDate);
	}

}
