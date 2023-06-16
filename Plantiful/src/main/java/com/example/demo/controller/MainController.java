package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.demo.chat.ChatRoomMap;
import com.example.demo.chat.dto.ChatDto;
import com.example.demo.chat.dto.ChatRoomDto;
import com.example.demo.chat.service.ChatServiceMain;
import com.example.demo.chat.service.MsgChatService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/chat")
public class MainController {
	private SimpMessageSendingOperations template;
	
	@Autowired
	private ChatServiceMain chatServiceMain;
	
	@Autowired MsgChatService msgChatService;
	
	/**
	  * 채팅 리스트 화면 "/" 로 요청이 들어오면 전체 채팅룸 리스트를 담아서 return
	 */
	@GetMapping("")
	public Map goChatRoom() {
		Map map = new HashMap();
		System.out.println("goChatRoom");
		map.put("list", chatServiceMain.findAllRoom());
		return map;
	}
	
	/**
	 * MessageMapping을 통해 webSocket으로 들어오는 메세지를 발신 처리한다.
	 * client에서는 /pub/chat/message로 요청하게 되고 이것을 controller가 처리
	 * 처리가 완료되면 /sub/chat/room/roomId로 메세지 전송
	 * @param ChatDto, SimpMessageHeaderAccessor
	 * @return void
	 */
	@MessageMapping("/enterUser")
	public void enterUser(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {
		//채팅방 유저 +1
		chatServiceMain.plusUserCnt(chat.getRoomId());
		
		// 채팅방에 유저 추가 및 UserUUID 반환
		String userUUID = msgChatService.addUser(ChatRoomMap.getInstance().getChatRooms(), chat.getRoomId(), chat.getSender());
		
		// return 결과를 socket session 에 userUUID로 저장
		headerAccessor.getSessionAttributes().put("userUUID", userUUID);
		headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());
		
		chat.setMessage(chat.getSender() + " 님이 입장하셨습니다.");
		template.convertAndSend("/sub/chat/room"+chat.getRoomId(), chat);
		
	}
	
	@MessageMapping("/sendMessage")
	public void sendMessage(@Payload ChatDto chat) {
		chat.setMessage(chat.getMessage());
		template.convertAndSend("/sub/chat/room" + chat.getRoomId(), chat);
	}
	
	/**
	 * 유저 퇴장시 EventListener 호출하여 퇴장 확인
	 * @param SessionDisconnectEvent 
	 * @return void
	 */
	@EventListener
	public void webSockectDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		
		// STOMP 세션에 있던 UUID와 roomID를 확인해서 채팅방 유저 리스트와 room에서 해당 유저 삭제
		String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
		String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
		
		// 채팅방 유저 -1
		chatServiceMain.minusUserCnt(roomId);
		
		// 채팅방 유저 리스트에서 UUID 유저 닉네임 조회 및 리스트에서 유저 삭제
		String userName = msgChatService.findUserNameByRoomIdAndUserUUID(ChatRoomMap.getInstance().getChatRooms(), roomId, userUUID);
		msgChatService.delUser(ChatRoomMap.getInstance().getChatRooms(), roomId, userUUID);
		if(userName != null) {
			ChatDto chat = ChatDto.builder().type(ChatDto.MessageType.LEAVE).sender(userName).message(userName + " 님이 퇴장하셨습니다.").build();
			template.convertAndSend("/sub/chat/room"+roomId,chat);
		}
	}
	
	/**
	 * 채팅에 참여한 유저 리스트 return
	 * @param String
	 * @return ArrayList<String> 
	 */
	@GetMapping("/userList")
	public ArrayList<String> userList(String roomId) {
		return msgChatService.getUserList(ChatRoomMap.getInstance().getChatRooms(), roomId);
	}
	
	/**
	 * 채팅방 생성
	 */
	@PostMapping("/createroom")
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
	
	@GetMapping("/chkUserCnt/{roomId}")
	public Map chkUserCnt(@PathVariable String roomId) {
		Map map = new HashMap();
		boolean flag = chatServiceMain.chkRoomUserCnt(roomId);
		map.put("flag", flag);
		return map;
	}
}
