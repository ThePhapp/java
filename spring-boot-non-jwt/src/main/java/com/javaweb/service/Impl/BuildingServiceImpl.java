package com.javaweb.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.Entity.BuildingEntity;
import com.javaweb.repository.Entity.DistrictEntity;
import com.javaweb.repository.Entity.RentAreaEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService{
	
	@Autowired
	private BuildingRepository buildingRepository;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private RentAreaRepository rentAreaRepository;
	
	@Override
	public List<BuildingDTO> findAll(@RequestParam Map<String, Object> params, List<String> typecode) {
		// TODO Auto-generated method stub
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(params, typecode);
		List<BuildingDTO> result = new ArrayList<BuildingDTO> ();
		for(BuildingEntity item : buildingEntities) {
			BuildingDTO building = new BuildingDTO();
			building.setName(item.getName());
			DistrictEntity districtEntity = districtRepository.findNameById(item.getDistrictid());
			building.setAddress(item.getStreet() + "," + item.getWard() + ", " + districtEntity.getName());
			List<RentAreaEntity> rentAreas = rentAreaRepository.getValueByBuildingId(item.getId());
			String areaResult = rentAreas.stream().map(it->it.getValue().toString()).collect(Collectors.joining(","));
			building.setRentArea(areaResult);
			result.add(building);
		}
		return result;
	}

}
