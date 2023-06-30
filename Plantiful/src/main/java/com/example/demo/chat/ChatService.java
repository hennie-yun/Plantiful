package com.example.demo.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.member.Member;
import com.example.demo.member.MemberDao;

@Service
public class ChatService {
	
	@Autowired
	private ChatDao dao;
	
	@Autowired
	private MemberDao memDao;
	
	@Autowired 
	private ChatRoomService roomService;
	
	public ChatDto chatting(ChatDto dto) {
		System.out.println("chatting 시작");
		System.out.println("chatting 내부 dto : " + dto);
		Chat chat = new Chat(0, dto.getRoom(), dto.getMember(), dto.getMessage(), dto.getSendTime(), dto.isRequest());
		String email = dto.getMember().getEmail();
		Member member = memDao.findById(email).orElse(null); 
		chat.setMember(member);
		Chat savedChat = dao.save(chat);
		System.out.println("savedChat : " + savedChat);
		roomService.saveLastMsg(savedChat.getRoom());
		if(savedChat != null) {
			ChatDto newDto = new ChatDto(savedChat.getNum(), savedChat.getRoom(), savedChat.getMember(), 
					savedChat.getMessage(), savedChat.getSendTime(), true);
			return newDto;
		} else {
			return null;
		}
	}

}
