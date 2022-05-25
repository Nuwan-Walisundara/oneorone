package com.dmz.charging.station.finder.service.model.custom;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
@JsonInclude
public class CompanyDto {

	
	@JsonIgnore
	private int parentId;
	private String name;
	private List<StationDTO> stations=new ArrayList<StationDTO>();
	private List<CompanyDto> childCompany;
	
	public void add(CompanyDto companyDto) {
		if(childCompany==null) {
			 childCompany=new ArrayList<CompanyDto>();
		}
		childCompany.add(companyDto);
	}
	

}
