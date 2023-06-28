package com.example.demo.chat;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.member.Member;

@Service
public class ChatRoomService {
	
	@Autowired
	private ChatRoomDao dao;
	
	@Autowired
	private ChatDao chatDao;
	
	public ArrayList<ChatRoomDto> findRoomById(String email) {
		ArrayList<ChatRoom> roomList = (ArrayList<ChatRoom>) dao.findByMember(email);
		if(roomList.isEmpty()) { 
			return null;
		}
		ArrayList<ChatRoomDto> dtoList = new ArrayList<>();
		for(ChatRoom room : roomList) {
			ChatRoomDto dto = new ChatRoomDto(room.getNum(), room.getLastMsg(), room.getLastSender(), room.getUserCount());
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	public ChatDto joinRoom(long roomNum, String email) {
		Chat chat = chatDao.save(new Chat(0, new ChatRoom(roomNum, "", new Member(email, "", "", "", 0, ""), 0), 
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
	
	public void saveLastMsg(ChatRoom room) {
		ChatRoom newRoom = dao.findById(room.getNum()).orElse(null);
		newRoom.setLastMsg(room.getLastMsg());
		newRoom.setLastSender(room.getLastSender());
		ChatRoom savedRoom = dao.save(newRoom);
		if(savedRoom == null) {
			System.out.println("null");
		}
	}
}
