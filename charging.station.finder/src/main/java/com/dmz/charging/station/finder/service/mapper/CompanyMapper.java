package com.dmz.charging.station.finder.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dmz.charging.station.finder.exception.BusinessException;
import com.dmz.charging.station.finder.service.model.custom.CompanyDto;
import com.dmz.charging.station.finder.service.model.domain.Company;

@Mapper(componentModel = "spring")
public abstract class  CompanyMapper {
	
	@Mapping(target = "stations", ignore = true)
	@Mapping(target = "childCompany", ignore = true)
	@Mapping(target = "parentId", source="parent.id")
	public abstract CompanyDto CompanyToCompanyDto(Company company)throws BusinessException;
	
}
