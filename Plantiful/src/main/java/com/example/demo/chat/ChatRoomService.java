package com.example.demo.chat;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.member.Member;
import com.example.demo.subscribeboard.SubscribeBoard;
import com.example.demo.subscribeboard.SubscribeBoardDto;

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
			ChatRoomDto dto = new ChatRoomDto(room.getNum(), room.getLastMsg(), room.getLastSender(), room.getSubscribeNum());
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	public ChatDto joinRoom(long roomNum, String email) {
		Chat chat = chatDao.save(new Chat(0, new ChatRoom(roomNum, "", new Member(email, "", "", "", 0, ""), null), 
				new Member(email, "", "", "", 0, ""), email+" 님이 입장하셨습니다", null, true ));
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
	
	public ChatRoomDto createRoom(SubscribeBoardDto boardDto) {
		SubscribeBoard board = new SubscribeBoard(boardDto.getSubscribe_num(), boardDto.getEmail(), boardDto.getTitle(), 
				boardDto.getSite(), boardDto.getTotal_point(), boardDto.getTotal_people(), 
				boardDto.getRegister_date(), boardDto.getRegister_date(), boardDto.getPayment_date(), 
				boardDto.getSubscribe_startdate(), boardDto.getSubscribe_enddate());
		ChatRoom room = new ChatRoom(0, null, null, board);
		ChatRoom entity = dao.findBySubscribeNum(room.getSubscribeNum());
		ChatRoomDto dto = null;
		if(entity == null) {
			ChatRoom savedRoom = dao.save(room);
			dto = new ChatRoomDto(savedRoom.getNum(), savedRoom.getLastMsg(), savedRoom.getLastSender(), savedRoom.getSubscribeNum());
		} else {
			dto = new ChatRoomDto(entity.getNum(), entity.getLastMsg(), entity.getLastSender(), entity.getSubscribeNum());
		}
		
		return dto;
	}

	public ArrayList<ChatRoomDto> findAll() {
		ArrayList<ChatRoom> roomList = (ArrayList<ChatRoom>) dao.findAll();
		ArrayList<ChatRoomDto> list = new ArrayList<>();
		roomList.forEach(t -> {
			ChatRoomDto dto = new ChatRoomDto(t.getNum(), t.getLastMsg(), t.getLastSender(), t.getSubscribeNum());
			list.add(dto);
		});
		
		return list;
	}
}
