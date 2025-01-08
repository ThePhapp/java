package com.javaweb.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.Beans.BuildingDTO;
import com.javaweb.customException.FieldRequiredException;

@RestController
public class BuildingAPI {

	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> building(@RequestParam(value = "name", required = false) String name,
						@RequestParam(value = "ward", required = false) String ward) {
		List<BuildingDTO> results = new ArrayList<> ();
		BuildingDTO result = new BuildingDTO();
		result.setName("Phap");
		result.setWard("The");
		result.setNumberOfStatement(3);
		results.add(result);
		BuildingDTO result1 = new BuildingDTO();
		result1.setName("Phttap");
		result1.setWard("Ttthe");
		result1.setNumberOfStatement(5);
		results.add(result1);
		
		return results;
	}
	
	@PostMapping(value = "/api/building/")
	public Object getBuilding(@RequestBody BuildingDTO building) {
		isValidate(building);
		return null;
	}
	
	@DeleteMapping(value = "/api/building/{id}")
	public void deleteBuilding(@PathVariable Integer id) {
		System.out.print("Da xoa thanh cong "+ id);
	}
	
	public void isValidate(BuildingDTO buildingDTO) {
		if(buildingDTO.getName() == null || buildingDTO.getName().equals("") || buildingDTO.getNumberOfStatement() == null) {
			throw new FieldRequiredException("Field required!");
		}
	}
}
