package com.example.demo.kakao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class baseController {
	@Autowired
	AuthService authSerivce;
	
	@Autowired
	CustomMessageSerivce customMessageService;
	
	@GetMapping("/")
	public String serviceStart(String code) {
		if(authSerivce.getKakaoAuthToken(code)) {
			customMessageService.sendMyMessage();
			return "메시지 전송 성공";
		} else {
			return "토큰발급 실패";
		}
	}
	
}
