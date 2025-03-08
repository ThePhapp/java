package com.javaweb.repository.Impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.Entity.BuildingEntity;
import com.javaweb.repository.Entity.DistrictEntity;
import com.javaweb.ultils.ConnectionJDBCUltil;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository {
	
	@Override
	public DistrictEntity findNameById(Long id) {
		String sql = "SELECT d.name FROM district d WHERE d.id = " + id + " ;";
		DistrictEntity districtEntity = new DistrictEntity();
		try(Connection conn = ConnectionJDBCUltil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {
			
			while (rs.next()) {
				districtEntity.setName(rs.getString("name"));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
		return districtEntity;
  }
}
