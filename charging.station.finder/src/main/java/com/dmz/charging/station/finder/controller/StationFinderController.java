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

import com.dmz.charging.station.finder.service.FinderService;
import com.dmz.charging.station.finder.service.model.custom.SearchResult;
import com.dmz.charging.station.finder.service.model.custom.StationFinderDTO;

@RestController
public class StationFinderController {
	@Autowired
	FinderService repo;
	
	@PostMapping("/")
	public SearchResult findNearestStations(@Valid @RequestBody StationFinderDTO searchDto) {
		
		return repo.find(searchDto);
	}
	
	/**
	 * Customise the validation error response.
	 * @param ex
	 * @return
	 */
	//@ResponseStatus(HttpStatus.BAD_REQUEST)
	//@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	       
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}

