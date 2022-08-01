package com.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.api.dto.UserRole;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@DynamicUpdate
@DynamicInsert
@Entity(name = "tb_user")
@Table(name="TB_USER")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;
	
	@Column(columnDefinition="VARCHAR(20)", length=20, nullable=false)
	private String userName;
	
	@Column(columnDefinition="VARCHAR(30)", length=30, nullable=false)
	private String userNickname;
	
	@Column(columnDefinition="VARCHAR(60)", length=60, nullable=false)
	private String password;
	
	@Column(columnDefinition="VARCHAR(20)", length=20, nullable=false)
	private String phoneNumber;
	
	@Column(columnDefinition="VARCHAR(100)", length=100, nullable=false)
	private String email;
	
	@Column(columnDefinition="VARCHAR(1)", length=1, nullable=true)
	private String gender;
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdDateTime;
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date updateDateTime;
	
	@Column(columnDefinition="TEXT")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_USER;
	
	@Transient
	private OrderEntity orderEntity;
	
	
	@Builder
	public UserEntity(String userName, String userNickname, String password, String phoneNumber, String email, String gender, UserRole role) {
		this.userName = userName;
		this.userNickname = userNickname;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.gender = gender;
		this.role = role;
	}
}
