package com.example.demo.chat.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.demo.chat.ChatRoomMap;
import com.example.demo.chat.dto.ChatRoomDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MsgChatService {
	public ChatRoomDto createChatRoom(String roomName, String roomPwd, int maxCount) {
		// roomName과 roomPwd로 빌드 후 return
		ChatRoomDto room = ChatRoomDto.builder()
				.roomId(UUID.randomUUID().toString()).roomName(roomName).maxCount(maxCount).userCount(0).build();
		room.setUserList(new ConcurrentHashMap<String, String>());
		ChatRoomMap.getInstance().getChatRooms().put(room.getRoomId(), room);
		return room;
	}
	
	/** 채팅방 유저 리스트에 유저 추가 */
	public String addUser(Map<String, ChatRoomDto> chatRoomMap, String roomId, String userName) {
		ChatRoomDto room = chatRoomMap.get(roomId);
		String userUUID = UUID.randomUUID().toString();
		
		// 아이디 중복 확인 후 userList 추가
		// room.getUserList().put(UserUUID, userName);
		
		ConcurrentHashMap<String, String> userList = (ConcurrentHashMap<String, String>)room.getUserList();
		userList.put(userUUID, userName);
		return userUUID;
	}
	
	/** 채팅방 유저 이름 중복 확인 */
	public boolean isDuplicateName(Map<String, ChatRoomDto> chatRoomMap, String roomId, String userName) {
		boolean flag = true;
		ChatRoomDto room = chatRoomMap.get(roomId);
		// 만약 userName이 중복이라면
		while(room.getUserList().containsValue(userName)) {
			flag = false;
		}
		
		return flag;
	}
	
	/**
	 * 채팅방 userName 조회
	 */
	public String findUserNameByRoomIdAndUserUUID(Map<String, ChatRoomDto> chatRoomMap, String roomId, String userUUID) {
		ChatRoomDto room = chatRoomMap.get(roomId);
		return (String) room.getUserList().get(userUUID);
	}
	
	/**
	 * 채팅방 전체 userList 조회
	 */
	public ArrayList<String> getUserList(Map<String, ChatRoomDto> chatRoomMap, String roomId) {
		ArrayList<String> list = new ArrayList<>();
		ChatRoomDto room = chatRoomMap.get(roomId);
		room.getUserList().forEach((key, value) -> list.add((String)value));
		return list;
	}
	
	/**
	 * 채팅방 특정 유저 삭제
	 */
	public void delUser(Map<String, ChatRoomDto> chatRoomMap, String roomId, String userUUID) {
		ChatRoomDto room = chatRoomMap.get(roomId);
		room.getUserList().remove(userUUID);
	}
}
