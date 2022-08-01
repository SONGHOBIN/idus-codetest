package com.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.entity.OrderEntity;
import com.api.service.OrderService;
import com.common.jwt.JwtProvider;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/order")
public class OrderController {
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	
	/**
	 * 단일 회원의 주문 목록 조회 : 헤더 JWT 토큰 등록 후 사용 가능
	 * @param request
	 * @return
	 */
	@Operation(summary = "4. 단일 회원의 주문 목록 조회 : 헤더 JWT 토큰 등록 후 사용 가능")
	@GetMapping(path="")
    public ResponseEntity<List<OrderEntity>> getListOrder (
    	HttpServletRequest request
    ) {
		List<OrderEntity> list = orderService.getListOrder(JwtProvider.getUserEmail(request.getHeader("Authorization")));
		
    	return new ResponseEntity<List<OrderEntity>>(list, HttpStatus.OK);
    }
}
