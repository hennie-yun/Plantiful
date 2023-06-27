package com.example.demo.naver;




import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class NaverController {
	
	@Autowired
	private NaverService service;
	private String state="";
	private String CLIENT_ID = "IiiFJKBOyzL3qvfXasPq";
	private String CLIENT_SECRET = "PtvyRoMmt_";
	private String TOKEN_REQUEST_URL = "http://localhost:8181/api/naver/callback";

@RequestMapping("/api/naver/go")
public Map state() {
	  state = service.generateRandomState();
	  Map map = new HashMap();
	  map.put("state", state);
      return map;
}
	
	
@RequestMapping("/api/naver/callback")
public ResponseEntity naverCallback(String code, String state) throws JsonProcessingException{
	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	params.add("grant_type", "authorize_code");
	params.add("client_id", CLIENT_ID);
	params.add("client_secret", CLIENT_SECRET);
	params.add("code", code);
	params.add("state", state);
	
	HttpEntity<MultiValueMap<String, String>> naverTokenRequest = makeTokenRequest(params);
	
	RestTemplate rt = new RestTemplate();
	ResponseEntity<String> tokenResponse = rt.exchange(
			TOKEN_REQUEST_URL,
			HttpMethod.POST,
			naverTokenRequest,
			String.class);
	
	ObjectMapper objectMapper = new ObjectMapper();
	 NaverOAuthToken naverToken = objectMapper.readValue(tokenResponse.getBody(), NaverOAuthToken.class);
	return tokenResponse;	
}
	 private HttpEntity<MultiValueMap<String, String>> makeTokenRequest(MultiValueMap<String, String> params){
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		 HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(params, headers);
		 return naverTokenRequest;
	 }
	

} 