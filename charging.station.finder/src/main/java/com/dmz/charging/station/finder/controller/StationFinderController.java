package com.dmz.charging.station.finder.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dmz.charging.station.finder.exception.BusinessException;
import com.dmz.charging.station.finder.service.FinderService;
import com.dmz.charging.station.finder.service.model.custom.SearchResult;
import com.dmz.charging.station.finder.service.model.custom.StationFinderDTO;

@RestController
public class StationFinderController {
	@Autowired
	FinderService repo;
	
	@PostMapping("/")
	public SearchResult findNearestStations(@Valid @RequestBody(required=true) StationFinderDTO searchDto) {
		
		
		  try {
				return repo.find(searchDto);
		    } catch (BusinessException ex) {
		        throw new ResponseStatusException(
		          HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
		    }
		  
	
	}
	
}

