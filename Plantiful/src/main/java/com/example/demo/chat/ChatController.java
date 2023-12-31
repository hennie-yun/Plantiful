package com.example.demo.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;

@Controller
public class ChatController {

	@Autowired
	private ChatService service;
	
	@Autowired
	private MemberService memService;
	
	/**
	 * receive로 들어온 메세지를 저장하고 sub라는 이름으로 구독 중인 모든 유저에게 메세지를 발송
	 * @param dto
	 * @return
	 */
	@MessageMapping("/receive")
	@SendTo("/sub")
	public ChatDto chatting(ChatDto dto) {
		String message = dto.getMessage();
		ChatRoom room = dto.getRoom();
		MemberDto memDto = memService.getMember(dto.getMember().getEmail());
		Member member = new Member(memDto.getEmail(), memDto.getPwd(), memDto.getNickname(), memDto.getPhone(), 
					memDto.getId() , memDto.getImg());
		room.setLastSender(member);
		room.setLastMsg(message);
		room.setLastSendTime(dto.getSendTime());
		dto.setRoom(room);
		return service.chatting(dto);
	}

}
