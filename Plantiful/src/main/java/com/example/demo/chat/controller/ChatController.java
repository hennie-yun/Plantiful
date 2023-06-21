package com.example.demo.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.demo.chat.dto.ChatDto;
import com.example.demo.chat.dto.ChatRoom;
import com.example.demo.chat.service.ChatRoomService;
import com.example.demo.chat.service.ChatService;

@Controller
public class ChatController {

	@Autowired
	private ChatService service;
	
	@Autowired 
	private ChatRoomService roomService;
	
	@MessageMapping("/receive")
	@SendTo("/sub")
	public ChatDto chatting(ChatDto dto) {
		String message = dto.getMessage();
		ChatRoom room = dto.getRoom();
		room.setLastMsg(message);
		roomService.saveLastMsg(dto.getRoom());
		return service.chatting(dto);
	}

}
