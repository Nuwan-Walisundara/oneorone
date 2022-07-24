package com.coffeeshop.station.dal.repository;

import org.springframework.data.repository.CrudRepository;

import com.coffeeshop.service.model.domain.Order;

public interface OrderRepo extends CrudRepository<Order, Long> {

}
