package com.example.demo.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.demo.chat.dto.ChatDto;
import com.example.demo.chat.service.ChatService;

@Controller
public class ChatController {

	@Autowired
	private ChatService service;
	
	@MessageMapping("/receive")
	@SendTo("/sub")
	public ChatDto chatting(ChatDto dto) {
		return service.chatting(dto);
	}

}
