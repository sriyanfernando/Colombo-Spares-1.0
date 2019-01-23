package com.javasampleapproach.mysql.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "customer")
@DynamicUpdate
public class Customer implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long customerId;

	@Column(name = "customername")
	@NotNull
	private String customerName;
/*
	@Column(name = "customernumber")
	@NotNull
	private String customerNumber;
	*/
	@Column(name = "email")
	@NotNull
	@Email
	private String email;

	@Column(name = "password")
	@NotNull
	//@JsonIgnore
	private String password;

	@Column(name = "active")
	@NotNull
	private Boolean active;

	@CreatedDate
	@Column(name = "creationDate")
	private Date creationDate;

	@LastModifiedDate
	@Column(name = "lastModifiedDate")
	private Date lastModifiedDate;

	
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "role_id")
	private Role roles;

	
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private Set<Device> devices;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private Set<Vehicle> vehicles;
	

	
	
	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getcustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
/*
	public String getcustomerNumber() {
		return customerNumber;
	}
	
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	*/
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean value) {
		this.active = value;
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

	public Set<Device> getDevices() {
		return devices;
	}

	public void setDevices(Set<Device> devices) {
		this.devices = devices;
	}

	public Set<Vehicle> getVehicles(){
		return vehicles;
	}
	public void setVehicles(Set<Vehicle> vehicles){
		this.vehicles=vehicles;
	}
	
	
	
	public Role getRoles() {
		return roles;
	}

	public void setRoles(Role roles) {
		this.roles = roles;
	}

	public Customer() {

	}

	@PrePersist
	void creationDate() {
		this.creationDate = this.lastModifiedDate = new Date();
	}

	@PreUpdate
	void lastModifiedDate() {
		this.lastModifiedDate = new Date();
	}
	/*
	public Customer(String customerNumber, String password) {
		this.customerNumber = customerNumber;
		this.password = password;
	}
	public String toString() {
		return String.format(
				"Customer[id=%d,customerNumber='%s' , password='%s', active='%s', creationDate='%s', lastModifiedDate='%s']",
				customerId, customerNumber, password, creationDate, lastModifiedDate, active);
	}
	*/

	public Customer(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String toString() {
		return String.format(
				"Customer[id=%d,email='%s' , password='%s', active='%s', creationDate='%s', lastModifiedDate='%s']",
				customerId, email, password, creationDate, lastModifiedDate, active);
	}
	

}
