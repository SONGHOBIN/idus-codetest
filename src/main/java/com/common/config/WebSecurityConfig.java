package com.common.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.common.jwt.JwtFilter;

@EnableWebSecurity // 웹 보안 활성화
public class WebSecurityConfig {
    private final JwtFilter jwtFilter;
    
    public WebSecurityConfig(JwtFilter jwtFilter) {
    	this.jwtFilter = jwtFilter;
    }
    
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Order(SecurityProperties.BASIC_AUTH_ORDER)
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.csrf().disable()
			.headers().frameOptions().disable()
			
			.and()
			.exceptionHandling()
			
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			
			.and()
			.authorizeRequests()
			.antMatchers("/user/**", "/order/**").hasRole("USER")
			.anyRequest().permitAll()
			
			.and()
			.apply(new SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>(){
				@Override
				public void configure(HttpSecurity http) {
					http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
				}
            });
		
		return http.build();
	}
}
