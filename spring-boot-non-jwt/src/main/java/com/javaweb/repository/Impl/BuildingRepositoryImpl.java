package com.javaweb.repository.Impl;

import java.lang.reflect.Field;
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

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.Entity.BuildingEntity;
import com.javaweb.ultils.ConnectionJDBCUltil;
import com.javaweb.ultils.NumbersUltil;
import com.javaweb.ultils.StringUltil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

	public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
		String staffId = buildingSearchBuilder.getStaffId().toString();
		if(StringUltil.checkString(staffId)) {
			sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		}
		
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if(typeCode != null && typeCode.size() != 0) {
			sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
		}
		
		String rentAreaTo = buildingSearchBuilder.getAreaTo().toString();
		String rentAreaFrom = buildingSearchBuilder.getAreaFrom().toString();
		if(StringUltil.checkString(rentAreaTo) || StringUltil.checkString(rentAreaFrom)) {
			sql.append(" INNER JOIN rentarea ON rentarea.buildingid = b.id ");
		}
	}
	
	public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for(Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				if(!fieldName.equals("staffId") && !fieldName.equals("typecode") && 
						!fieldName.startsWith("area") && !fieldName.startsWith("rentPrice")) {
					String value = item.get(buildingSearchBuilder).toString();
					if(NumbersUltil.isNumber(value) == true) {
						where.append(" AND b. " + fieldName + " = " + value);
					} else {
						where.append(" AND b. " + fieldName + " LIKE '%" + value + "%'");
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		String staffId = buildingSearchBuilder.getStaffId().toString();
		if(StringUltil.checkString(staffId)) {
			where.append(" AND assignmentbuilding.staffid = " + staffId);
		}
		String rentAreaTo = buildingSearchBuilder.getAreaTo().toString();
		String rentAreaFrom = buildingSearchBuilder.getAreaFrom().toString();
		if(StringUltil.checkString(rentAreaTo) || StringUltil.checkString(rentAreaFrom)) {
			if(StringUltil.checkString(rentAreaFrom)) {
				where.append(" AND rentarea.value >= " + rentAreaFrom);
			}
			if(StringUltil.checkString(rentAreaTo)) {
				where.append(" AND rentarea.value <= " + rentAreaTo);
			}
		}
		
		String rentPriceFrom = buildingSearchBuilder.getRentPriceFrom().toString();
		String rentPriceTo = buildingSearchBuilder.getRentPriceTo().toString();
		if(StringUltil.checkString(rentPriceTo) || StringUltil.checkString(rentPriceFrom)) {
			if(StringUltil.checkString(rentPriceFrom)) {
				where.append(" AND b,rentprice >= " + rentPriceFrom);
			}
			if(StringUltil.checkString(rentPriceTo)) {
				where.append(" AND b.rentprice <= " + rentPriceTo);
			}
		}
		List<String> typecode = buildingSearchBuilder.getTypeCode();
		if(typecode != null && typecode.size() != 0) {
			List<String> listTypeCode = new ArrayList<>();
			for(String item : typecode) {
				listTypeCode.add("'" + item + "'");
			}
			where.append(" AND renttype.code IN (" + String.join(",", listTypeCode) +")");
		}
	}
	
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, b.floorarea, "
												+ " b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee FROM building b ");
		joinTable(buildingSearchBuilder, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNormal(buildingSearchBuilder, where);
		querySpecial(buildingSearchBuilder, where);
		sql.append(where);
		List<BuildingEntity> result = new ArrayList<>();
		try(Connection conn = ConnectionJDBCUltil.getConnection();
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
	          //buildingEntity.setRentPrice(rs.getLong("rentprice")); 
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
