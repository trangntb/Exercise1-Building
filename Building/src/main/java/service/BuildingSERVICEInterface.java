package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.BuildingDAO;
import dao.BuildingDAOInterface;
import model.BuildingSearchInput;
import model.BuildingSearchOutput;

public class BuildingSERVICEInterface implements SERVICEInterface<BuildingSearchInput,BuildingSearchOutput>  
{
	
	private String tranform(String type) {
		String[] typeCode = {"tang-tret", "noi-that", "nguyen-can"};
		String[] typeName = {"Tầng trệt","Nguyên căn","Nội thất"};
		Map<String,String> tranform = new HashMap<>();
		for(int i=0;i<typeCode.length;i++)
			tranform.put(typeCode[i],typeName[i]);	
		
		
		String result ="";
		String[] types = type.split(",");
		for(int i=0;i<types.length-1;i++) {
			System.out.println(  tranform.get(types[i].trim()) );
			result += tranform.get( types[i].trim() )  +", ";
		}
		return result += tranform.get( types[types.length-1].trim() );
	}
	
	public static BuildingSERVICEInterface getInstance() {
		return new BuildingSERVICEInterface();
	}
	
	@Override
	public List<BuildingSearchOutput> selectBy(BuildingSearchInput buildingSearchInput) {
		
		BuildingDAO buildingDAO = new BuildingDAO(null,buildingSearchInput.getName(),
				buildingSearchInput.getStreet(),buildingSearchInput.getWard(),buildingSearchInput.getDistrict(),
				buildingSearchInput.getFloorarea(),buildingSearchInput.getType());
		
		List<BuildingDAO> buildingDAOs = BuildingDAOInterface.getInstance().selectBy(buildingDAO);
		
		List<BuildingSearchOutput> results = new ArrayList<>();
		
		for(BuildingDAO item: buildingDAOs) {
			BuildingSearchOutput  buildingSearchOutput = new BuildingSearchOutput();
			buildingSearchOutput.setName(item.getName());
			buildingSearchOutput.setAddress(item.getStreet() + " - " + item.getWard() + " - "+ item.getDistrict());
			String type = tranform(item.getType());
			buildingSearchOutput.setType(type);
			
			results.add(buildingSearchOutput);
		}
		return results;
	}
}
