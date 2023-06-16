package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.demo.chat.ChatRoomMap;
import com.example.demo.chat.dto.ChatDto;
import com.example.demo.chat.dto.Chat;
import com.example.demo.chat.service.ChatServiceMain;
import com.example.demo.chat.service.MsgChatService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ChatController {
	private final SimpMessageSendingOperations template;
    private final MsgChatService msgChatService;
    private final ChatServiceMain chatServiceMain;
	
	/**
	 * MessageMapping을 통해 webSocket으로 들어오는 메세지를 발신 처리한다.
	 * client에서는 /pub/chat/message로 요청하게 되고 이것을 controller가 처리
	 * 처리가 완료되면 /sub/chat/room/roomId로 메세지 전송
	 * @param ChatDto, SimpMessageHeaderAccessor
	 * @return void
	 */
	@MessageMapping("/chat/enterUser")
	public void enterUser(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {
		//채팅방 유저 +1
		chatServiceMain.plusUserCnt(chat.getRoomId());
		
		// 채팅방에 유저 추가 및 UserUUID 반환
		String userUUID = msgChatService.addUser(ChatRoomMap.getInstance().getChatRooms(), chat.getRoomId(), chat.getMember().getEmail());
		
		// return 결과를 socket session 에 userUUID로 저장
		headerAccessor.getSessionAttributes().put("userUUID", userUUID);
		headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());
		
		chat.setMessage(chat.getMember() + " 님이 입장하셨습니다.");
		template.convertAndSend("/sub/chat/room"+chat.getRoomId(), chat);
		
	}
	
	@MessageMapping("/chat/sendMessage")
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
		System.out.println(userName);
		if(userName != null) {
			ChatDto chat = ChatDto.builder().type(ChatDto.MessageType.LEAVE).member(null).message(userName + " 님이 퇴장하셨습니다.").build();
			template.convertAndSend("/sub/chat/room"+roomId,chat);
		}
	}
	
	/**
	 * 채팅에 참여한 유저 리스트 return
	 * @param String
	 * @return ArrayList<String> 
	 */
	@GetMapping("/chat/userList")
	public ArrayList<String> userList(String roomId) {
		return msgChatService.getUserList(ChatRoomMap.getInstance().getChatRooms(), roomId);
	}
	
	@MessageMapping("/receive")
    @SendTo("/send")
    public Chat chatting(Chat chat) {
        return msgChatService.chatting(chat);
    }
}
