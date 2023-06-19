package com.example.demo.kakao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomMessageSerivce {
	@Autowired
	MessageService messageService;
	
	public boolean sendMyMessage() {
		DefaultMessageDto myMsg = new DefaultMessageDto();
		myMsg.setBtnTitle("자세히보기");
		myMsg.setMobileUrl("");
		myMsg.setObjType("text");
		myMsg.setWebUrl("");
		myMsg.setText("메시지 테스트입니다.");
		
		String accessToken = AuthService.authToken;
		
		return messageService.sendMessage(accessToken, myMsg);
		
	}
	
}
