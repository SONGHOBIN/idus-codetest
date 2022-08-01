package com.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	@Query("select r from tb_order r where r.userIdx = (select idx from tb_user where email = :email)")
	List<OrderEntity> findAllByEmail(@Param("email") String email);
}
