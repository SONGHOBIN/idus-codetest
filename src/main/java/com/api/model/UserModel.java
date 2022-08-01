package com.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description="회원 등록 정보")
@Data
public class UserModel {
	@Schema(description="이름", example="테스트")
	@NotBlank
	@Pattern(regexp="^[A-Zㄱ-ㅎ가-힣]*${1,20}")
	private String userName;
	
	@Schema(description="닉네임", example="test")
	@NotBlank
	@Pattern(regexp="^[a-z]*${1,30}")
	private String userNickname;
	
	@Schema(description="비밀번호", example="A1q2w3e4r!@")
	@NotBlank
	@Pattern(regexp="(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{10,60}")
	private String password;
	
	@Schema(description="전화번호", example="01012345678")
	@NotBlank
	@Pattern(regexp="[0-9]{9,20}")
	private String phoneNumber;
	
	@Schema(description="이메일", example="test@naver.com")
	@NotBlank
	@Email
	private String email;
	
	@Schema(description="성별('M' : 남자, 'F' : 여자, '' : 선택 안함)", example="M")
	@NotBlank
	private String gender;
}
