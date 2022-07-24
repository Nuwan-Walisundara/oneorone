package com.coffeeshop.station.dal.repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffeeshop.exception.BusinessException;
import com.coffeeshop.service.mapper.OrderMapper;
import com.coffeeshop.service.model.custom.OrderDTO;
import com.coffeeshop.service.model.domain.Order;

@Service
public class OrderDAO {

	@Autowired
	OrderRepo orderRepo;

	@Autowired
	OrderMapper companyMapper;

	public Order update(Order order, OrderDTO dto,String user) throws BusinessException {

		Order orderUpdated =populateOrder( order,  dto, user);

		return update(orderUpdated);
	}
	
	
	private Order populateOrder(Order order,OrderDTO dto,   String user) throws BusinessException {

		order.setStatus(dto.getStatus());

		if (dto.getRemarks()!=null) {
			order.setRemarks(order.getRemarks() + " /n Shop comment" + dto.getRemarks());
		}
		order.setOrderReadyBy(user);
		
		order.setOrderReadyTime(Timestamp.from(Instant.now()));

		
		return order;
	}
	
	
	public Order update(Order order) throws BusinessException {

		orderRepo.save(order);

		return order;
	}
	public Order getOrderById(Long orderId) {
		Optional<Order> orderOp = orderRepo.findById(orderId);
		if (orderOp.isPresent()) {
			return orderOp.get();

		} 
		return null;
	}
}
