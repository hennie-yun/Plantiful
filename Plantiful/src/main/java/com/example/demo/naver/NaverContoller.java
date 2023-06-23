package com.example.demo.naver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.JwtTokenProvider;


@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/api/naver")
public class NaverContoller{
	private String NAVER_AUTH_URI = "https://nid.naver.com";
	private String clientId = "IiiFJKBOyzL3qvfXasPq";
	private String clientSecret = "PtvyRoMmt_";
	
	@Autowired
	private JwtTokenProvider provider;
	
	@Autowired
	private NaverService service;
	
	@GetMapping("/email")
	public Map getEmail(@RequestHeader("token") String token) {
		String email = provider.getUsernameFromToken(token);
		Map map = new HashMap();
		map.put("email", email);
		return map;
	}

	@GetMapping("/oauth")
	public Map oauth() {
		
		URL url = new URL();
	}
	
	
	
}
