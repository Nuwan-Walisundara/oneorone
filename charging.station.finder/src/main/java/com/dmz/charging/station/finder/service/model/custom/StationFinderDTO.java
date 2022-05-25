package com.dmz.charging.station.finder.service.model.custom;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StationFinderDTO {

/**
 * prefferd company id for search
 */ @NotBlank(message = "Company id is mandetory")
	private String companyID;
	 

/**
 * Latitude and longitude are a pair of numbers (coordinates) used to describe a 
 * position on the plane of a geographic coordinate system. 
 * The numbers are in decimal degrees format and range from -90 to 90 for latitude and -180 to 180 for longitude.
 */
	 @Min(value = -90, message = "Latitude can't be less than -90 degrees")
	 @Max(value = 90, message = "Latitude can't be greter than 90 degrees")
	private double latitude;
	
	 @Min(value = -180, message = "longitude can't be less than -180 degrees")
	 @Max(value = 180, message = "longitude can't be greter than 180 degrees")
	private double longitude;
	 
	 /**
	  * For calculation purpose latitude value in degrees into convert into Radian.
	  * Formula :1° × π/180 = 0.01745rad
	  */
	 private double latitudeInRadian;
	 
	 /**
	  * For calculation purpose logitude value in degrees into convert into Radian.
	  * Formula :1° × π/180 = 0.01745rad
	  */
	 private double longitudeInRadian;
	 
	 /**
	  * Radius in KMs for search
	  */
	private double preferredRadius;
	 
}
