package com.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.entity.UserEntity;
import com.api.service.UserService;
import com.common.jwt.JwtProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	
	/**
	 * 단일 회원 상세 정보 조회 : 헤더 JWT 토큰 등록 후 사용 가능
	 * @param request
	 * @return
	 */
	@Operation(summary = "3. 단일 회원 상세 정보 조회 : 헤더 JWT 토큰 등록 후 사용 가능")
	@GetMapping(path="")
    public ResponseEntity<Optional<UserEntity>> getUser (
    	HttpServletRequest request
    ) {
		Optional<UserEntity> user = userService.getUser(JwtProvider.getUserEmail(request.getHeader("Authorization")));
    	
		return new ResponseEntity<Optional<UserEntity>>(user, HttpStatus.OK);
    }
	
	
	/**
	 * 여러 회원 목록 조회 : 헤더 JWT 토큰 등록 후 사용 가능
	 * @param userName
	 * @param email
	 * @param page
	 * @param size
	 * @return
	 */
	@Operation(summary = "5. 여러 회원 목록 조회 : 헤더 JWT 토큰 등록 후 사용 가능")
	@GetMapping(path="/all")
	public ResponseEntity<List<UserEntity>> getUsers (
		@Parameter(description="회원 이름", required=false) @RequestParam(required=false) String userName, 
		@Parameter(description="회원 이메일", required=false) @RequestParam(required=false) String email,
		@Parameter(description="페이지 번호", required=true, example="1") @RequestParam(required=true) Integer page,
		@Parameter(description="한 페이지에서 나타내는 원소의 수 (게시글 수)", required=true, example="10") @RequestParam(required=true) Integer size
    ) {
		Map<String, Object> params = new HashMap<>();
		
		params.put("userName", userName);
		params.put("email", email);
		params.put("page", page);
		params.put("size", size);
		
    	return new ResponseEntity<List<UserEntity>>(userService.getUsers(params), HttpStatus.OK);
    }
}
