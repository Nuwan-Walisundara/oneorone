package com.dmz.charging.station.finder.service.model.custom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationDTO {
	@JsonIgnore
	private Long id;	
	@JsonIgnore
	private Long company_id;	
	private String name;
	private String distanceFromCurrentlocation	;
	private double longitude;
	private double latitude;
	
	
}
