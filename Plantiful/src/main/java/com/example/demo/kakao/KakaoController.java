package com.example.demo.kakao;

import java.net.http.HttpHeaders;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class KakaoController {
	
	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code) { //Data를 리턴해주는 컨트롤러 함수
		
		//post 방식으로 key = value 데이터를 요청 (카카오 쪽으로)
		// 이 때 필요한 라이브러리가 RestTemplate, 얘를 쓰면 http 요청을 편하게 할 수 있다
		RestTemplate rt = new RestTemplate();
		
		//HTTP POST를 요청할 때 보내는 데이터(BODY0를 설명해주는 헤더도 만들어 같이 보내줘야함
		HttpHeaders headers = new HttpHeaders();
		
		
	}
}
