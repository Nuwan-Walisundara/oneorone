package com.dmz.charging.station.finder.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dmz.charging.station.finder.exception.BusinessException;
import com.dmz.charging.station.finder.service.FinderService;
import com.dmz.charging.station.finder.service.model.custom.SearchResult;
import com.dmz.charging.station.finder.service.model.custom.StationFinderDTO;

@RestController
public class StationFinderController {
	/**
	 * service layer reference
	 */
	@Autowired
	FinderService repo;
	
	Logger logger = LoggerFactory.getLogger(StationFinderController.class);
	
	/**
	 * 
	 * @param searchDto
	 * @return
	 */
	@PostMapping("/")
	public SearchResult findNearestStations(@Valid @RequestBody(required=true) StationFinderDTO searchDto) {
		
		
		  try {
			  /**
			   * trigger find service 
			   */
				return repo.find(searchDto);
				
		    } catch (BusinessException ex) {
		    	logger.error("",ex);
		    	
		        throw new ResponseStatusException(
		          HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
		    }
		  
	
	}
	
}

