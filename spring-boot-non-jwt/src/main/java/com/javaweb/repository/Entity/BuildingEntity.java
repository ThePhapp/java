package com.javaweb.repository.Entity;

public class BuildingEntity {
	private String name;
	private Integer NumberOfStatement;
	private String ward;
	private String street;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumberOfStatement() {
		return NumberOfStatement;
	}
	public void setNumberOfStatement(Integer numberOfStatement) {
		NumberOfStatement = numberOfStatement;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
}
