package com.javaweb.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.Entity.BuildingEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService{
	
	@Autowired
	private BuildingRepository buildingRepository;
	@Override
	public List<BuildingDTO> findAll(@RequestParam Map<String, Object> params, List<String> typecode) {
		// TODO Auto-generated method stub
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(params, typecode);
		List<BuildingDTO> result = new ArrayList<> ();
		for(BuildingEntity item : buildingEntities) {
			BuildingDTO building = new BuildingDTO();
			building.setName(item.getName());
			building.setAddress(item.getStreet() + "," + item.getWard());
			result.add(building);
		}
		return result;
	}

}
