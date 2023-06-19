package com.example.demo.chat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.chat.dto.ChatDto;
import com.example.demo.chat.service.ChatService;

@Controller
public class ChatController {

	@Autowired
	private ChatService service;
	
	@MessageMapping("/receive")
	@SendTo("/send")
	public ChatDto chatting(ChatDto dto) {
		return service.chatting(dto);
	}
	
	@RequestMapping("/chat/msglist")
	@ResponseBody
	public Map findMsgByRoom(long roomNum) {
		Map map = new HashMap();
		ArrayList<ChatDto> list = service.findMsgByRoom(roomNum);
		map.put("list", list);
		return map;
	}
}
