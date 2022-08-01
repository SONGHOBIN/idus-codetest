package com.api.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@DynamicUpdate
@DynamicInsert
@Entity(name = "tb_order")
@Table(name="TB_ORDER", indexes = @Index(name="i_order", columnList="userIdx"))
public class OrderEntity {
	@Id
	@Column(columnDefinition="VARCHAR(12)", length=12, nullable=false)
	private String orderNo;
	
	@Column(columnDefinition="BIGINT", nullable=false)
	private Long userIdx;
	
	@Column(columnDefinition="VARCHAR(100)", length=100, nullable=false)
	private String productName;
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime orderDateTime;
	
	
	@Builder
	public OrderEntity(String orderNo, Long userIdx, String productName, LocalDateTime orderDateTime) {
		this.orderNo = orderNo;
		this.userIdx = userIdx;
		this.productName = productName;
		this.orderDateTime = orderDateTime;
	}
}