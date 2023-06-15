package com.example.demo.chat.service;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Service;

import com.example.demo.chat.ChatRoomMap;
import com.example.demo.chat.dto.ChatRoomDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public class ChatServiceMain {
	private final MsgChatService msgChatService;
	
	/** 
	 * 전체 채팅방 조회 
	 * @return ArrayList<ChatRoomDto>
	 * */
	public ArrayList<ChatRoomDto> findAllRoom() {
		ArrayList<ChatRoomDto> rooms = new ArrayList<>(ChatRoomMap.getInstance().getChatRooms().values());
		Collections.reverse(rooms);
		return rooms;
	}
	
	/**
	 * roomID 기준으로 채팅방 찾기
	 * @param String
	 * @return ChatRoomDto 
	 */
	public ChatRoomDto findRoomById(String roomId) {
		return ChatRoomMap.getInstance().getChatRooms().get(roomId);
	}
	
	/**
	 * roomName 로 채팅방 만들기 
	 * @param String, String, int
	 * @return ChatRoomDto
	 * */
	public ChatRoomDto createChatRoom(String roomName, String roomPwd, int maxUserCnt){
    	ChatRoomDto room = msgChatService.createChatRoom(roomName, roomPwd, maxUserCnt);
        return room;
	}
	
	
	/**
	 * maxCount에 따른 채팅창 입장 여부
	 * @param String
	 * @return boolean
	 */
	public boolean chkRoomUserCnt(String roomId) {
		ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
		if(room.getUserCount() + 1 > room.getMaxCount()) {
			return false;
		}
		
		return true;
	}
	
	/** 
	 * 채팅방 인원 +1 
	 * @param String
	 * @return void
	 */
	public void plusUserCnt(String roomId) {
		log.info("cnt {}", ChatRoomMap.getInstance().getChatRooms().get(roomId).getUserCount());
		ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
		room.setUserCount(room.getUserCount()+1);
	}
	
	/**
	 * 채팅방 인원 -1 이후 채팅방 인원이 0명이면 채팅방 삭제
	 * @param roomId
	 */
	public void minusUserCnt(String roomId) {
		ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
		room.setUserCount(room.getUserCount()-1);
		if(room.getUserCount() == 0) {
			ChatRoomMap.getInstance().getChatRooms().remove(roomId);
		}
	}

	/** 
	 * 채팅방 삭제 
	 * @param String
	 * @return void
	 * */
	public void delChatRoom(String roomId) {
		try {
			ChatRoomMap.getInstance().getChatRooms().remove(roomId);
			log.info("삭제 완료 roomId : {}", roomId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
