package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.chat.ChatRoomMap;
import com.example.demo.chat.dto.ChatRoomDto;
import com.example.demo.chat.service.ChatServiceMain;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatServiceMain chatServiceMain;

	/**
	 * 채팅방 생성
	 */
	@PostMapping("/chat/createroom")
	public void createRoom(
			@RequestParam("roomName") String roomName, 
			@RequestParam(value = "maxUserCnt", defaultValue = "2") int maxUserCnt,
			@RequestParam("chatType") String chatType,
			RedirectAttributes rttr, HttpServletResponse response
			) {
		
		// 매개변수 : 방이름, 인원수
		ChatRoomDto room;
		room = chatServiceMain.createChatRoom(roomName, "", maxUserCnt);
		rttr.addFlashAttribute("roomName", room);
		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 채팅방을 찾아서 client를 chatroom 으로 보낸다
	 * @param roomId
	 * @return String
	 */
	@GetMapping("/chat/room")
	public Map roomDetail(String roomId, HttpSession session, HttpServletResponse response) {
		String loginId = (String)session.getAttribute("loginId");
		if(loginId == null) {
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Map map = new HashMap();
		ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
		map.put("room", room);
		return map;
	}
	
	@GetMapping("/chat/chkUserCnt/{roomId}")
	public Map chkUserCnt(@PathVariable String roomId) {
		Map map = new HashMap();
		boolean flag = chatServiceMain.chkRoomUserCnt(roomId);
		map.put("flag", flag);
		return map;
	}
}
