package com.dmz.charging.station.finder.service.model.domain;

import java.util.List;

import com.dmz.charging.station.finder.service.model.custom.CompanyDto;
import com.dmz.charging.station.finder.service.model.custom.StationDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Station {
	
	public Station(Long id,String name,double latitude,double longitude,Long company_id) {
		this.id= id;
		this.name =name;
		this.latitudeInRadian = latitude;
		this.longitudeInRadian =longitude;
		this.company_id=company_id;
	}
	
	private Long id ;
	private String  name;
	private double   latitude;
	private double   longitude;
	private double   latitudeInRadian;
	private double   longitudeInRadian;
	private Long company_id;
 
	
	
}
