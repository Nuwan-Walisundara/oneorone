package com.coffeeshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffeeshop.exception.BusinessException;
import com.coffeeshop.service.mapper.OrderMapper;
import com.coffeeshop.service.model.custom.MessageDTO;
import com.coffeeshop.service.model.custom.OrderDTO;
import com.coffeeshop.service.model.domain.Order;
import com.coffeeshop.station.dal.repository.OrderDAO;

@Service
class OrderImpl implements OrderService {


	@Autowired
	OrderMapper companyMapper;
	
	@Autowired
	OrderDAO dao;

	@Autowired
	NotificationService notificationService;
/**
 * update the order processing status , remarks will be appends to existing.
 */
	public OrderDTO processOrder(OrderDTO dto, String shopCode, String user) throws BusinessException {

		Order order = dao.getOrderById(dto.getOrderId());
	 
		/**
		 * patch the order status , remarks 
		 */
		dao.update(order, dto, user);
		
		/**
		 * Notify the intended party abt the status update
		 */
		notificationService.notify(new MessageDTO(dto, shopCode));
		
		return dto;
	}

}
