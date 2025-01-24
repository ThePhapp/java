package com.javaweb.Model;

public class BuildingDTO {
	private String name;
	private Integer numberOfStatement;
	private String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumberOfStatement() {
		return numberOfStatement;
	}
	public void setNumberOfStatement(Integer numberOfStatement) {
		this.numberOfStatement = numberOfStatement;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
