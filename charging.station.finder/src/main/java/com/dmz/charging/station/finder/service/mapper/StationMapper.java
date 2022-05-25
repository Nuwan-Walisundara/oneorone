package com.dmz.charging.station.finder.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.dmz.charging.station.finder.service.model.custom.StationDTO;
import com.dmz.charging.station.finder.service.model.domain.Station;

@Mapper(componentModel = "spring")
public interface  StationMapper {
	
	@Mapping(target = "latitude", source = "latitudeInRadian",qualifiedByName = "radianToDegreeMapper")
	@Mapping(target = "longitude", source = "longitudeInRadian",qualifiedByName = "radianToDegreeMapper")
	@Mapping(target = "distanceFromCurrentlocation", ignore = true)
	public  StationDTO stationToStationDTO(Station station);
	

	@Named("radianToDegreeMapper")
	 public static  double toDegrees(double radian) {
		return Math.toDegrees(radian);
	}
	

	
}
