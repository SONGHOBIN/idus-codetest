package com.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.dto.UserRole;
import com.api.entity.OrderEntity;
import com.api.entity.UserEntity;
import com.api.mapper.UserMapper;
import com.api.model.LoginModel;
import com.api.model.UserModel;
import com.api.repository.OrderRepository;
import com.api.repository.UserRepository;
import com.common.jwt.JwtProvider;

@Service
public class UserService {
	// Reposiroty
	private final UserRepository userRepository;
	private final OrderRepository orderRepository;
	
	// Mapper
	private final UserMapper userMapper;
	
	public UserService(UserRepository userRepository, OrderRepository orderRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
		
		this.userMapper = userMapper;
	}
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
	
	
	/**
	 * 회원가입
	 * @param userModel
	 * @return
	 */
	@Transactional
	public void join(UserModel userModel) throws Exception {
		userRepository.findByEmail(userModel.getEmail()).ifPresent(m -> {
			throw new IllegalStateException("이미 존재하는 회원");
		});
		
		// 회원 정보 등록
		UserEntity user = userRepository.save(UserEntity.builder()
				.userName(userModel.getUserName())
				.userNickname(userModel.getUserNickname())
				.password(passwordEncoder.encode(userModel.getPassword()))
                .phoneNumber(userModel.getPhoneNumber())
                .email(userModel.getEmail())
                .gender(userModel.getGender())
                .role(UserRole.ROLE_USER)
                .build());
		
		
		// 임의 주문 정보 등록
		orderRepository.save(OrderEntity.builder()
				.orderNo(this.createOrderNo())
				.userIdx(user.getIdx())
				.productName("짜장면")
				.orderDateTime(LocalDateTime.now())
				.build());
		
		orderRepository.save(OrderEntity.builder()
				.orderNo(this.createOrderNo())
				.userIdx(user.getIdx())
				.productName("짬뽕")
				.orderDateTime(LocalDateTime.now())
				.build());
	}

	
	/**
	 * 로그인(토큰발행)
	 * @param loginModel
	 * @return
	 */
	public String login(LoginModel loginModel) {
		UsernamePasswordAuthenticationToken authenticationToken = 
						new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword());
		
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		
		// 토큰 발행
		return JwtProvider.createToken(authentication);
	}

	public Optional<UserEntity> getUser(String email) {
		return userRepository.findByEmail(email);
	}

	public List<UserEntity> getUsers(Map<String, Object> params) {
		
		return userMapper.selectUsers(params);
	}
	
	
	// 주문번호 생성
	static int n = 12;
	static char[] chs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	public String createOrderNo() {
		Random rd = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			char ch = chs[rd.nextInt(chs.length)];
			sb.append(ch);
		}
		return sb.toString();
	}
}
