package com.dmz.charging.station.finder.service;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmz.charging.station.finder.dal.ChargingStationDAL;
import com.dmz.charging.station.finder.dal.CompanyDAL;
import com.dmz.charging.station.finder.exception.BusinessException;
import com.dmz.charging.station.finder.service.mapper.CompanyMapper;
import com.dmz.charging.station.finder.service.mapper.StationMapper;
import com.dmz.charging.station.finder.service.model.custom.CompanyDto;
import com.dmz.charging.station.finder.service.model.custom.SearchResult;
import com.dmz.charging.station.finder.service.model.custom.StationDTO;
import com.dmz.charging.station.finder.service.model.custom.StationFinderDTO;
import com.dmz.charging.station.finder.service.model.domain.Company;
import com.dmz.charging.station.finder.service.model.domain.Station;

@Service
 class ChargingStationFinderImpl implements FinderService {

	private static final String DISTANCE_IN_KM_POST_FIX =  " KM";

	@Autowired
	CompanyDAL companyDal;
	
	@Autowired
	ChargingStationDAL chargingStationsDal;
	
	@Autowired
	CompanyMapper companyMapper;
	
	@Autowired
	StationMapper stationMapper;
	
	public StationMapper getStationMapper() {
		return stationMapper;
	}
	
	 /**
	  * This will query the db for matching nearest  stations.
	  */
	@Override
	public SearchResult find(StationFinderDTO searchDto) throws BusinessException{
		
		/**
		 * convert the latitude longitude values from Degree to Radian
		 */
		
		
		final double latituedeInRadian = toRadians(searchDto.getLatitude());
		final double longitudeInRadian = toRadians(searchDto.getLongitude());
		
		/**
		 * Set the calculated Radian values for future calculations
		 */
		
		searchDto.setLatitudeInRadian(latituedeInRadian);
		searchDto.setLongitudeInRadian(longitudeInRadian);
		
		
		/**
		 * Query all the possible Child company from the hierarchy  from the persistence(DB).
		 */
		List<Long> companyIdList = companyDal.findChildCompaniesByParent( searchDto.getCompanyID());
		
		if(companyIdList.isEmpty()) {
			return new SearchResult();
		}
		/**
		 * Query the station 
		 */
		List<Station> stationList= chargingStationsDal.loadNearestStations(searchDto,companyIdList);
		
		/**
		 * group the station domain object into stationDto map
		 */
		 Map<Long,List<StationDTO>>  mapOfStations = toStationDTOs(stationList, searchDto);
		
		 /**
		  * construct final object hierarchy
		  */
		 CompanyDto  companyDTOs = toCompanyDTO( searchDto.getCompanyID(),   mapOfStations);
		 
		 
		 if(companyDTOs==null) {//if search is empty
			return new SearchResult();
		 }else {
			 return new SearchResult(companyDTOs);
		 }
		 
	}
	

	/**
	 *The method perform following tasks:
	 *	1. Prepare the domain object into DTO.
	 *	2. Calculate the distance(in KM) to the given lat/long.
	 *	3. Group the each station by the company id. 
	 * @param stationList
	 * @return
	 */
	private Map<Long,List<StationDTO>> toStationDTOs(List<Station> stationList,StationFinderDTO searchDto)throws BusinessException{
		
		Map<Long,List<StationDTO>>  returnMap= new HashMap<Long,List<StationDTO>>();
		
		/**
		 * iterate through each Station object
		 */
		 for(Station station: stationList) {
			 
			 /**
			  * Convert into dto
			  */
			 StationDTO dto = getStationMapper().stationToStationDTO(station);
			 
			 /**
			  * calculate the distance in km
			  */
			 dto.setDistanceFromCurrentlocation(calculateDistance(station,searchDto));
			 
			 /**
			  * Group by company
			  */
			 
			 if(returnMap.containsKey(station.getCompany_id())) {
				 
				 //Already exists in the Map,Add to existing list
				 returnMap.get(station.getCompany_id()) .add(dto);
				 
			 }else {
				 //create new list and add under new company id
				 returnMap.put(station.getCompany_id(),Arrays.asList(dto));
				 
			 }
			 
		 }
		return returnMap;
		
	}


	/**
	 * The shortest distance (the geodesic) between two given points 
	 * 		P1=(lat1, lon1) and P2=(lat2, lon2) on the surface of a sphere with radius R is the great circle distance. 
	 * 		It can be calculated using the formula:
	 *
	 *			dist = arccos(sin(lat1) · sin(lat2) + cos(lat1) · cos(lat2) · cos(lon1 - lon2)) · R
	 * @param station
	 * @param searchDto
	 * @return
	 */
	private String calculateDistance(Station station,StationFinderDTO searchDto ) throws BusinessException{
		//Radius of the earth
		final double R=6371;
		//@formatter:off
		final double distance= acos(sin(searchDto.getLatitudeInRadian()) 
								* sin(station.getLatitudeInRadian()) 
								+ cos(searchDto.getLatitudeInRadian()) 
								* cos(station.getLatitudeInRadian()) 
								* cos(searchDto.getLongitudeInRadian() 
									- station.getLongitudeInRadian())) * R;
		//@formatter:on
		return distance + DISTANCE_IN_KM_POST_FIX;
	}
	
	/**
	 * Convert the Company into CompanyDTO,add the stationDto.
	 * @param companyList
	 * @param mapOfStations
	 * @return
	 */
	private 	CompanyDto toCompanyDTO(String companyId,Map<Long,List<StationDTO>>  mapOfStations)throws BusinessException{
		
		CompanyDto parentCompanyDto= null;
		
		Company rootCompany = companyDal.findRootById(companyId);
		
		if(rootCompany !=null) {
			parentCompanyDto = companyMapper.CompanyToCompanyDto(rootCompany);
			if(mapOfStations.containsKey(rootCompany.getId())) {
				parentCompanyDto.setStations(mapOfStations.get(rootCompany.getId()));
				
			}
		}
		
		for(Company sub:rootCompany.getSubordinates()) {
	
			buildHierarchy(sub,parentCompanyDto,mapOfStations);
		}
		
		return parentCompanyDto;
		
	}
	private void buildHierarchy(Company company,CompanyDto parentDto ,Map<Long,List<StationDTO>>  mapOfStations  )throws BusinessException {
		
		List<StationDTO> stations;
		
		if(mapOfStations.containsKey(company.getId())) {
			stations = mapOfStations.get(company.getId());
			
		}else {
			/**
			 * if company not included in the nearest charging station map, ignore form continuing.
			 */
			return;
		}
		
		CompanyDto companyDto= companyMapper.CompanyToCompanyDto(company);
		companyDto.setStations(stations);
		
		
		parentDto.add(companyDto);
		
		if(!company.getSubordinates().isEmpty()) {
		
		
			for(Company sub: company.getSubordinates()) {
				 buildHierarchy(sub,companyDto ,mapOfStations  );
			}
		}
		
	}
	
	/**
	 * @param company
	 * @param parentDto
	 */
	/*private void buildHierarchy(Company company,CompanyDto parentDto   ) {
		if(company.getParent_company_id()==parentDto.getParentId()) {
		
		CompanyDto companyDto= companyMapper.CompanyToCompanyDto(company);
		parentDto.add(companyDto);

		}
		//@formatter:off
		
		List<Company> nextLevelCompanyList = companyList.stream()
														.filter(a-> a.getParent_company_id()==parentDto.getParentId())
														.collect(Collectors.toList());
		
		
		List<CompanyDto>nextLevelCompanyDtoList= nextLevelCompanyList.stream()
																	.map(companyMapper::CompanyToCompanyDto)
																	.collect(Collectors.toList());
		//@formatter:on
		
		
		companyList.removeAll(nextLevelCompanyList);
		parentDto.setChildCompany(nextLevelCompanyDtoList);
		
	
		
		
		
	}
	*/
	
}
