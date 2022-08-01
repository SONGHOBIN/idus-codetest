package com.api.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.model.LoginModel;
import com.api.model.UserModel;
import com.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class MainController {
	private final UserService userService;
	
	public MainController(UserService userService) {
		this.userService = userService;
	}
	
	
	/**
	 * 회원가입
	 * @param userModel
	 * @return
	 * @throws Exception
	 */
	@Operation(summary = "1. 회원가입")
	@PostMapping(path="/join")
    public ResponseEntity<Object> join (
		@Valid @RequestBody UserModel userModel
    ) throws Exception {
		userService.join(userModel);
		
    	return new ResponseEntity<Object>(HttpStatus.OK);
    }
	
	
	/**
	 * 로그인 : JWT 토큰 발행
	 * @param loginModel
	 * @return
	 */
	@Operation(summary = "2. 로그인 : JWT 토큰 발행")
	@PostMapping(path="/token")
    public ResponseEntity<String> login (
    	@RequestBody LoginModel loginModel
    ) {
    	return new ResponseEntity<String>(userService.login(loginModel), HttpStatus.OK);
    }
	
}
