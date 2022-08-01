package com.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.entity.OrderEntity;
import com.api.repository.OrderRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
	
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	/**
	 * 단일 회원의 주문 목록 조회 : 다중
	 * @param email
	 * @return
	 */
	public List<OrderEntity> getListOrder(String email) {
		
		return orderRepository.findAllByEmail(email);
	}
}
