package com.javasampleapproach.mysql.model;

import java.io.Serializable;


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

import javax.persistence.Table;



@Entity
@Table(name = "Part")
public class Part implements Serializable
{
	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "part_id")
	private long partId;

	@Column(name = "part_name")
	private String partName;
		
	@Column(name = "part_type")
	private String partType;
	  
	@Column(name = "part_year")
	private String partYear;
	
	@Column(name = "part_price")
	private long partPrice;
	
	@Column(name = "part_location")
    @Enumerated(EnumType.STRING)
    private PartLocation partLocation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicleId")
	private Vehicle vehicle;
	
	
	public enum PartLocation
	{
		Front,Back,Middle
		
	}

//-----------------------------------------------------------------------
	public Part(){
		
	}
	
	public Part(String name) {
		this.partName = name;
	}

	public long getPartId() {
		return partId;
	}

	public void setPartId(long partId) {
		this.partId = partId;
	}

	
	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartType() {
		return partType;
			
	}
	public void setPartType(String partType) {
		
		this.partType=partType;
	}
	
	public String getPartYear() {
		return partYear;
			
	}
	public void setPartYear(String partYear) {
		
		this.partYear=partYear;
	}
	
	public long getPartPrice() {
		return partPrice;
	}

	public void setPartPrice(long partPrice) {
		this.partPrice = partPrice;
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public PartLocation getPartLocation() {
		return partLocation;
	}

	public void setPartLocation(PartLocation partLocation) {
		this.partLocation = partLocation;
	}


	
	@Override
	public String toString() {
		return String.format(
				"Part[partId='%i', partName='%s' , partType='%s']",
				partId,  partName, partType);
	}
	
	
}
