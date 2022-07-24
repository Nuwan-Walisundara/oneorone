package com.coffeeshop.service;

import com.coffeeshop.exception.BusinessException;
import com.coffeeshop.service.model.custom.OrderDTO;

public interface OrderService {
	public OrderDTO processOrder(OrderDTO dto,String shopCode,String user)throws BusinessException ;

}
