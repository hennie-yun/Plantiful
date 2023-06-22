package com.example.demo.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.chat.dao.ChatDao;
import com.example.demo.chat.dto.Chat;
import com.example.demo.chat.dto.ChatDto;
import com.example.demo.chat.dto.ChatRoom;
import com.example.demo.member.Member;
import com.example.demo.member.MemberDao;

@Service
public class ChatService {
	
	@Autowired
	private ChatDao dao;
	
	@Autowired
	private MemberDao memDao;
	
	public ChatDto chatting(ChatDto dto) {
		dto.setRoom(new ChatRoom(1, dto.getMessage(), 0));
		Chat chat = new Chat(0, dto.getRoom(), dto.getMember(), dto.getMessage(), dto.getSendTime(), dto.isRequest());
		String email = dto.getMember().getEmail();
		Member member = memDao.findById(email).orElse(null); 
		chat.setMember(member);
		Chat savedChat = dao.save(chat);
		if(savedChat != null) {
			ChatDto newDto = new ChatDto(savedChat.getNum(), savedChat.getRoom(), savedChat.getMember(), 
					savedChat.getMessage(), savedChat.getSendTime(), true);
			System.out.println(newDto.getSendTime());
			return newDto;
		} else {
			return null;
		}
	}

}
