package com.example.demo.chat.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.chat.dao.ChatDao;
import com.example.demo.chat.dao.ChatRoomDao;
import com.example.demo.chat.dto.Chat;
import com.example.demo.chat.dto.ChatDto;
import com.example.demo.chat.dto.ChatRoom;
import com.example.demo.chat.dto.ChatRoomDto;
import com.example.demo.member.Member;

@Service
public class ChatRoomService {
	
	@Autowired
	private ChatRoomDao dao;
	
	@Autowired
	private ChatDao chatDao;
	
	public ArrayList<ChatRoomDto> findRoomById(Member member) {
		ArrayList<ChatRoom> roomList = (ArrayList<ChatRoom>) dao.findByMember(member);
		if(roomList.isEmpty()) { 
			return null;
		}
		ArrayList<ChatRoomDto> dtoList = new ArrayList<>();
		for(ChatRoom room : roomList) {
			ChatRoomDto dto = new ChatRoomDto(room.getNum(), room.getLastMsg(), room.getUserCount());
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	public ChatDto joinRoom(long roomNum, String email) {
		Chat chat = chatDao.save(new Chat(0, new ChatRoom(roomNum, "", 0), 
				new Member(email, "", "", "", 0, ""), email+" 입장", null, true ));
		ChatDto dto = new ChatDto(chat.getNum(), chat.getRoom(), chat.getMember(), chat.getMessage(), chat.getSendTime(), chat.isRequest());
		return dto;
	}

	public ArrayList<ChatDto> findChatByRoomNum(long roomNum) {
		ArrayList<Chat> list = chatDao.findAllByRoomNumOrderBySendTime(roomNum);
		ArrayList<ChatDto> dtoList = new ArrayList<>();
		for(Chat chat : list) {
			ChatDto dto = new ChatDto(chat.getNum(), chat.getRoom(), chat.getMember(), chat.getMessage(), chat.getSendTime(), chat.isRequest());
			dtoList.add(dto);
		}
		
		return dtoList;
	}
}
