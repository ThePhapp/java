package com.javaweb.repository;

import java.util.List;
import java.util.Map;

import com.javaweb.repository.Entity.BuildingEntity;

public interface BuildingRepository {
	List<BuildingEntity> findAll(Map<String, Object> params, List<String> typecode);
}
