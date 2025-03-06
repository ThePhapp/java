package com.javaweb.repository.Impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.Entity.BuildingEntity;
import com.javaweb.ultils.NumbersUltil;
import com.javaweb.ultils.StringUltil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	static final String USER = "root";
	static final String PASS = "1104205tP";
	
	public static void joinTable(Map<String, Object> params, List<String> typecode, StringBuilder sql) {
		String staffId = (String)params.get("staffId");
		if(StringUltil.checkString(staffId)) {
			sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		}
		
		if(typecode != null && typecode.size() != 0) {
			sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
		}
		
		String rentAreaTo = (String)params.get("areaTo");
		String rentAreaFrom = (String)params.get("areaFrom");
		if(StringUltil.checkString(rentAreaTo) || StringUltil.checkString(rentAreaFrom)) {
			sql.append(" INNER JOIN rentarea ON rentarea.buildingid = b.id ");
		}
	}
	
	public static void queryNormal(Map<String, Object> params, StringBuilder where) {
		for(Map.Entry<String, Object> it : params.entrySet()) {
			if(!it.getKey().equals("staffId") && !it.getKey().equals("typecode") && 
					!it.getKey().startsWith("area") && !it.getKey().startsWith("rentPrice")) {
				String value = it.getValue().toString();
				if(NumbersUltil.isNumber(value) == true) {
					where.append(" AND b. " + it.getKey() + " = " + value);
				} else {
					where.append(" AND b. " + it.getKey() + " LIKE '%" + value + "%'");
				}
			}
		}
	}
	
	public static void querySpecial(Map<String, Object> params, List<String> typecode, StringBuilder where) {
		String staffId = (String)params.get("staffId");
		if(StringUltil.checkString(staffId)) {
			where.append(" AND assignmentbuilding.staffid = " + staffId);
		}
		String rentAreaTo = (String)params.get("areaTo");
		String rentAreaFrom = (String)params.get("areaFrom");
		if(StringUltil.checkString(rentAreaTo) || StringUltil.checkString(rentAreaFrom)) {
			if(StringUltil.checkString(rentAreaFrom)) {
				where.append(" AND rentarea.value >= " + rentAreaFrom);
			}
			if(StringUltil.checkString(rentAreaTo)) {
				where.append(" AND rentarea.value <= " + rentAreaTo);
			}
		}
		
		String rentPriceFrom = (String)params.get("rentPriceFrom");
		String rentPriceTo = (String)params.get("rentPriceTo");
		if(StringUltil.checkString(rentPriceTo) || StringUltil.checkString(rentPriceFrom)) {
			if(StringUltil.checkString(rentPriceFrom)) {
				where.append(" AND b,rentprice >= " + rentPriceFrom);
			}
			if(StringUltil.checkString(rentPriceTo)) {
				where.append(" AND b.rentprice <= " + rentPriceTo);
			}
		}
		if(typecode != null && typecode.size() != 0) {
			List<String> listTypeCode = new ArrayList<>();
			for(String item : typecode) {
				listTypeCode.add("'" + item + "'");
			}
			where.append(" AND renttype.code IN (" + String.join(",", listTypeCode) +")");
		}
	}
	
	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typecode) {
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, b.floorarea, "
												+ " b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee FROM building b ");
		joinTable(params, typecode, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNormal(params, where);
		querySpecial(params, typecode, where);
		sql.append(where);
		List<BuildingEntity> result = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {
			
			while (rs.next()) {
				BuildingEntity buildingEntity = new BuildingEntity();
	            buildingEntity.setId(rs.getLong("id")); 
	            buildingEntity.setName(rs.getString("name")); 
	            buildingEntity.setWard(rs.getString("ward")); 
	            buildingEntity.setDistrictid(rs.getLong("districtid")); 
	            buildingEntity.setStreet(rs.getString("street")); 
	            buildingEntity.setFloorArea(rs.getLong("floorarea")); 
	            buildingEntity.setRentPrice(rs.getLong("rentprice")); 
	            buildingEntity.setServicefee(rs.getString("servicefee")); 
	            buildingEntity.setBrokerageFee(rs.getLong("brokeragefee")); 
	            buildingEntity.setManagerName(rs.getString("managername")); 
	            buildingEntity.setManagerPhoneNumber(rs.getString("managerphonenumber"));

	            result.add(buildingEntity);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
		return result;
  }
}
