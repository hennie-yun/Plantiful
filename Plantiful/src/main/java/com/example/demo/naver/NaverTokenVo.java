package com.example.demo.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverTokenVo {
	private String access_token;
	private String refresh_token;
	private String token_type;
	private int expires_in;
}
