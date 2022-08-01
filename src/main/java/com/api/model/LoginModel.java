package com.api.model;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description="로그인 정보")
@Data
public class LoginModel {
	@Schema(description = "이메일", example="test@naver.com")
	@NotBlank
	private String email;
	
	@Schema(description = "비밀번호", example="A1q2w3e4r!@")
	@NotBlank
	private String password;
}
