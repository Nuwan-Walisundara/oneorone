package com.coffeeshop.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.coffeeshop.exception.BusinessException;
import com.coffeeshop.service.OrderService;
import com.coffeeshop.service.model.custom.OrderDTO;

@RestController
public class OrderController {
	/**
	 * service layer reference
	 */
	@Autowired
	OrderService repo;

	Logger logger = LoggerFactory.getLogger(OrderController.class);

	/**
	 * 
	 * @param searchDto
	 * @return
	 */
	@PostMapping("/processOrder")
	public OrderDTO processOrder(@Valid @RequestBody(required = true) OrderDTO searchDto,
			@RequestHeader("shopCode") String shopCode, @RequestHeader("user") String user) {

		try {
			logger.info("Request "+ searchDto + " | shopCode : "+shopCode+" | user : " +user);
			/**
			 * trigger order service
			 */
			return repo.processOrder(searchDto, shopCode, user);

		} catch (BusinessException ex) {
			logger.error("", ex);

			throw new ResponseStatusException(ex.getError().getHttpStatus(), ex.getError().getDesc(),ex);
		}

	}

}
