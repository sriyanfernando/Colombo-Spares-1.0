package com.javasampleapproach.mysql.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="Vehicle")

public class Vehicle implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "vehicle_id")
	private long vehicleId;

	@Column(name = "vehicle_name")
	private String vehicleName;
	
	@Column(name = "vehicle_model")
	private String vehicleModel;
	
	@Column(name = "vehicle_type")
	private String vehicleType;
	
	@Column(name = "vehicle_year")
	private String vehicleYear;
	
	//@Column(name = "vehicle_type")+-
	//@Enumerated(EnumType.STRING)
	//private VehicleType vehicleType;
	
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle")
	private Set<Part> parts;
	
	
	
//-------------------------------------------------------------------	
		
	
	public Vehicle() {
	}

	public Vehicle(String name) {
		this.vehicleName = name;
	}

	public long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	
	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	
	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleYear() {
		return vehicleYear;
	}

	public void setVehicleYear(String vehicleYear) {
		this.vehicleYear = vehicleYear;
	}
	

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = (Timestamp)creationDate;
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
	
	
	public Set<Part> getParts() {
		return parts;
	}

	public void setParts(Set<Part> parts) {
		this.parts = parts;
	}
	
	@Override
	public String toString() {
		return String.format("Vehicle[vehicleId='%d', vehicleName='%s',vehicleModel='%s',vehicleType='%s',vehicleYear='%s',creationDate='%s', lastModifiedDate='%s']", vehicleId, vehicleName,vehicleModel,vehicleType,vehicleYear,creationDate, lastModifiedDate);
	}
	
}
