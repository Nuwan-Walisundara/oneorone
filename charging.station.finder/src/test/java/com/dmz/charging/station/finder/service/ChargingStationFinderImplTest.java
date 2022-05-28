package com.dmz.charging.station.finder.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.dmz.charging.station.finder.dal.CompanyDAL;
import com.dmz.charging.station.finder.dal.CompanyRepository;
import com.dmz.charging.station.finder.exception.BusinessException;
import com.dmz.charging.station.finder.service.model.custom.CompanyDto;
import com.dmz.charging.station.finder.service.model.custom.SearchResult;
import com.dmz.charging.station.finder.service.model.custom.StationFinderDTO;

@SpringBootTest
//@Sql({ "/data_pub.sql"})
public class ChargingStationFinderImplTest {

	@Mock
	CompanyDAL companyDal;
	@Mock
	CompanyRepository companyRepository;


	@InjectMocks
	@Autowired
	ChargingStationFinderImpl chargingStationFinderimpl;

	@DisplayName("When user provide all infomation corectly- then retun result")
	@Test
	@Transactional
	public void test1() {
		try {
			StationFinderDTO searchDto = new StationFinderDTO();
			searchDto.setCompanyID("SVPEAB");
			searchDto.setPreferredRadius(10);
			searchDto.setLatitude(59.40172547799233);
			searchDto.setLongitude(17.94621121167337);
			
			assertNotNull(chargingStationFinderimpl.find(searchDto).getCompany());
			assertTrue (chargingStationFinderimpl.find(searchDto).getCompany().getName().equalsIgnoreCase("Svenska Petroleum Exploration AB"));
			
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@DisplayName("When user provide all infomation corectly- then retun correct  company & station details")
	@Test
	@Transactional
	public void test2() {
		try {
			StationFinderDTO searchDto = new StationFinderDTO();
			searchDto.setCompanyID("SVPEAB");
			searchDto.setPreferredRadius(11);
			searchDto.setLatitude(59.40172547799233);
			searchDto.setLongitude(17.94621121167337);
			CompanyDto dto = chargingStationFinderimpl.find(searchDto).getCompany() ;
			final int numberOfStations =dto.getStations().size() + dto.getChildCompany().get(0).getStations().size();
			assertTrue(numberOfStations==2,"Invalid number of station count");
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@DisplayName("When user search  with invalid company code- then zero company")
	@Test
	@Transactional
	public void test3() {
		try {
			StationFinderDTO searchDto = new StationFinderDTO();
			searchDto.setCompanyID("SVPEAB3");
			searchDto.setPreferredRadius(11);
			searchDto.setLatitude(59.40172547799233);
			searchDto.setLongitude(17.94621121167337);
		  
			assertNull(chargingStationFinderimpl.find(searchDto).getCompany(),"Invalid Company returned");
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@DisplayName("When user search  with company code, codinate ,prferd distance- then only nearest stations should return")
	@Test
	@Transactional
	public void test4() {
		try {
			StationFinderDTO searchDto = new StationFinderDTO();
			searchDto.setCompanyID("SVPEAB");
			searchDto.setPreferredRadius(10);
			searchDto.setLatitude(59.40172547799233);
			searchDto.setLongitude(17.94621121167337);
			SearchResult dto = chargingStationFinderimpl.find(searchDto) ;
		
			
			assertNotNull(dto.getCompany(),"Invalid Company returned");
			
			assertNotNull(dto.getCompany().getChildCompany(),"Invalid Company returned");
			assertNull(dto.getCompany().getStations(),"Invalid Company returned");
			assertTrue(dto.getCompany().getChildCompany().size()==1);
			
			final int numberOfStations =  dto.getCompany().getChildCompany().get(0).getStations().size();
			assertTrue(numberOfStations==1,"Only the nearest station should returned");			
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@DisplayName("When user search  with company code, codinate ,with out prferd distance- then all stations should return")
	@Test
	@Transactional
	public void test5() {
		try {
			StationFinderDTO searchDto = new StationFinderDTO();
			searchDto.setCompanyID("SVPEAB");
		//	searchDto.setPreferredRadius(10);
			searchDto.setLatitude(59.40172547799233);
			searchDto.setLongitude(17.94621121167337);
			SearchResult dto = chargingStationFinderimpl.find(searchDto) ;
		
			
			assertNotNull(dto.getCompany(),"Invalid Company returned");
			
			assertNotNull(dto.getCompany().getChildCompany(),"Invalid Company returned");
			assertNotNull(dto.getCompany().getStations(),"Invalid Company returned");
			assertTrue(dto.getCompany().getChildCompany().size()==1);
			
			final int numberOfStations = dto.getCompany().getStations().size()+ dto.getCompany().getChildCompany().get(0).getStations().size();
			assertTrue(numberOfStations==2,"All stations should returned");			
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
