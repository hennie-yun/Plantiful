package com.example.demo.chat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.auth.JwtTokenProvider;
import com.example.demo.chat.dto.ChatDto;
import com.example.demo.chat.dto.ChatRoomDto;
import com.example.demo.chat.service.ChatRoomService;
import com.example.demo.member.Member;

@Controller
@CrossOrigin(origins = "*")
public class ChatRoomController {
	
	@Autowired
	private ChatRoomService service;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@RequestMapping("/chat/roomlist")
	@ResponseBody
	public Map findRoomById(Member member) {
		Map map = new HashMap();
		ArrayList<ChatRoomDto> list = service.findRoomById(member);
		map.put("list", list);
		return map;
	}
	
	@RequestMapping("chat/joinroom")
	@ResponseBody
	public Map joinRoom(long roomNum, @RequestHeader(name = "token", required = false) String token) {
//		String email = tokenProvider.getUsernameFromToken(token);
//		ChatDto dto = service.joinRoom(roomNum, email);
		ArrayList<ChatDto> list = service.findChatByRoomNum(roomNum);
		Map map = new HashMap();
//		map.put("dto", dto);
		map.put("list", list);
		return map;
	}
}
