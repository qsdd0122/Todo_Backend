package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

import com.example.demo.security.JwtAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		// http 시큐리티 빌더
		http.cors() // WebMvcConfig에서 이미 설정했으므로 기본 cors 설정
			.and()
			.csrf()
				.disable()
			.httpBasic()
				.disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session기반이 아님을 선언
			.and()
			.authorizeRequests()
				.antMatchers("/", "/auth/**").permitAll() // /와 /auth/** 경로는 인증 안해도 됨
			.anyRequest().authenticated(); // 이외의 모든 경로는 인증해야함
		
		// filter 등록, 매 요청마다 CorsFilter 실행한 후 jwtAuthenticationFilter 실행
		http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
	}
	
	
}
