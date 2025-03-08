package com.javaweb.converter;

import java.util.List;
import java.util.Map;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.ultils.MapUltil;

public class BuildingSearchBuilderConverter {

	public BuildingSearchBuilder toBuildingSearchBuilder(Map<String, Object> params, List<String> typeCode) {
		BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder().setName(MapUltil.getObject(params, "name", String.class))
																							.setFloorArea(MapUltil.getObject(params, "floorArea", Long.class))
																							.setward(MapUltil.getObject(params, "ward", String.class))
																							.setStreet (MapUltil.getObject(params, "street", String.class)) 
																							.setDistrictcode(MapUltil.getObject(params, "districtcode", String.class)) 
																							.setNumberOfBasement (MapUltil.getObject(params, "numberofbasement", Integer.class)) 
																							.setTypeCode(typeCode) 
																							.setManagerName(MapUltil.getObject(params, "managername", String.class)) 
																							.setManagerPhoneNumber (MapUltil.getObject(params, "managerphonenumber", String.class)) 
																							.setRentPriceTo(MapUltil.getObject(params, "rentpriceto", Long.class)) 
																							.setRentPriceFrom(MapUltil.getObject(params, "rentpricefrom", Long.class)) 
																							.setAreaFrom(MapUltil.getObject(params, "areafrom", Long.class)) 
																							.setAreaTo(MapUltil.getObject (params, "areato", Long.class)) 
																							.setStaffId(MapUltil.getObject(params, "staffid", Long.class)) 
																							.build();
																							return null;
	}
}
