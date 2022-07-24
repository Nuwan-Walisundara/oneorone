package com.coffeeshop.service.mapper;

import org.mapstruct.Mapper;

import com.coffeeshop.exception.BusinessException;
import com.coffeeshop.service.model.custom.OrderDTO;
import com.coffeeshop.service.model.domain.Order;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

	public abstract Order map(OrderDTO order) throws BusinessException;

}
