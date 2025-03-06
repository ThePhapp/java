package com.javaweb.Model;

public class BuildingDTO {
	private String name;
	private Integer numberOfBasement;
	private String address;
	private String managerName;
	private String managerPhonenumber;
	private Integer floorArea;
	private String serviceFee;
	private String rentPrice;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}
	public void setNumberOfBasement(Integer numberOfBasement) {
		this.numberOfBasement = numberOfBasement;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerPhonenumber() {
		return managerPhonenumber;
	}
	public void setManagerPhonenumber(String managerPhonenumber) {
		this.managerPhonenumber = managerPhonenumber;
	}
	public Integer getFloorArea() {
		return floorArea;
	}
	public void setFloorArea(Integer floorArea) {
		this.floorArea = floorArea;
	}
	public String getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(String rentPrice) {
		this.rentPrice = rentPrice;
	}
	
}
