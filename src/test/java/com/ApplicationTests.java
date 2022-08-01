package com;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.common.jwt.JwtProvider;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {
	@Autowired
    private MockMvc mockMvc;
	
	/**
	 * 1. 회원 가입
	 * @throws Exception
	 */
	@DisplayName("1. 회원 가입")
	@Test
	@Order(1)
	public void test_1() throws Exception {
		for(int i = 1; i < 10; i++) {
			String content = "{\"userName\": \"테스트\", \"userNickname\": \"test\", \"password\": \"A1q2w3e4r!@\", \"phoneNumber\": \"010111111" + i + "\", \"email\": \"test" + i + "@naver.com\", \"gender\": \"M\"}";
			RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/join")
							.contentType(MediaType.APPLICATION_JSON)
							.content(content);
			
			mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print());
		}
	}

	
	/**
	 * 2. 회원 로그인(토큰발행)
	 * @throws Exception
	 */
	@DisplayName("2. 회원 로그인(토큰발행)")
	@Test
	@Order(2)
	public void test_2() throws Exception {
		String content = "{\"email\": \"test1@naver.com\", \"password\": \"A1q2w3e4r!@\"}";
		RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/token")
						.contentType(MediaType.APPLICATION_JSON)
						.content(content);
		
		
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print());
	}


	
	@Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
	
	private static String token;
	
	/**
	 * 3. 단일 회원 상세 정보 조회
	 * @throws Exception
	 */
	@DisplayName("3. 단일 회원 상세 정보 조회")
	@Test
	@Order(3)
	public void test_3() throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("test1@naver.com", "A1q2w3e4r!@");
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		token = JwtProvider.createToken(authentication);
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/user")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token);
		
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print());
	}
	
	
	/**
	 * 4. 단일 회원의 주문 목록 조회
	 * @throws Exception
	 */
	@DisplayName("4. 단일 회원의 주문 목록 조회")
	@Test
	@Order(4)
	public void test_4() throws Exception {
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/order")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token);
		
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print());
	}
	
	
	/**
	 * 5. 단일 회원의 주문 목록 조회
	 * - 페이지네이션으로 일정 단위로 조회합니다.
	 * - 이름, 이메일을 이용하여 검색 기능이 필요합니다.
	 * - 각 회원의 마지막 주문 정보
	 * @throws Exception
	 */
	@DisplayName("5. 여러 회원 목록 조회")
	@Test
	@Order(5)
	public void test_5() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		
		params.add("userName", "테스트");
		params.add("email", "test");
		params.add("page", "1");
		params.add("size", "5");
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/user/all")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token)
						.params(params);
		
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print());
	}
}
