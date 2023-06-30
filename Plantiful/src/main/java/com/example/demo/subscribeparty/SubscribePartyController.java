package com.example.demo.subscribeparty;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.chat.ChatDto;
import com.example.demo.chat.ChatRoom;
import com.example.demo.chat.ChatRoomDto;
import com.example.demo.chat.ChatRoomService;
import com.example.demo.chat.ChatService;
import com.example.demo.member.Member;
import com.example.demo.member.MemberService;
import com.example.demo.subscribeboard.SubscribeBoardDto;
import com.example.demo.subscribeboard.SubscribeBoardService;

@RestController
@Controller
@RequestMapping("/subscribeparty")
public class SubscribePartyController {

	
	@Autowired
	private SubscribePartyService service; 
	@Autowired
	private SubscribeBoardService sbservice;
	@Autowired
	private ChatRoomService roomService;
	@Autowired
	private MemberService memService;
	@Autowired
	private ChatService chatService;
	
	//추가
	@PostMapping("")
	public Map add(SubscribePartyDto dto) {	
		System.out.println(dto);
		//포인트 빠져나감 넣기
		SubscribePartyDto dto2 = service.save(dto);
		Map map = new HashMap();
		if(dto2 != null) {
			map.put("dto", dto2);
		}
		return map;
	}
	
	
	//파티 시작 여부 수정 1. 
	//인원수 기간안에 안차면 false로 보냄, 인원수 모집 기간안에 차면 true로 보냄  
	@PatchMapping("/{subscribe_num}/{flag}") //컬럼 일부 수정
	public Map editStart(@PathVariable("subscribe_num") int subscribe_num, @PathVariable("flag") int flag) {
		try {
			if (flag == 1) {
				service.editStart(subscribe_num); //시작중 (1)로 수정
				ArrayList<ChatRoomDto> roomList = roomService.findAll();
				boolean isCreated = false;
				for(ChatRoomDto dto : roomList) {
					if(dto.getSubscribeNum().getSubscribeNum() == subscribe_num) {
						isCreated = true;
					}
				}
				
				if(isCreated == false) {
					SubscribeBoardDto boardDto = sbservice.getBoard(subscribe_num);
					ArrayList<SubscribePartyDto> partyList = service.getBySubNum(subscribe_num);
					ChatRoomDto roomDto = roomService.createRoom(boardDto);
					ChatRoom room = new ChatRoom(roomDto.getNum(), "", null, roomDto.getSubscribeNum());
					partyList.forEach(t -> {
						Member member = t.getEmail();
						room.setLastSender(member);
						room.setLastMsg(member.getNickname() + " 님이 입장하셨습니다.");
						Timestamp time = new Timestamp(System.currentTimeMillis());
						ChatDto chatDto = new ChatDto(0, room, member, room.getLastMsg(), time, false);
						ChatDto chatted = chatService.chatting(chatDto);
						System.out.println("chatted 실행됨 : "+chatted);
					});
				}
			} else if (flag == 2){
				service.endStart(subscribe_num); //끝남 (2) 로 수정 
			} else {
				System.out.println("no change start_check");
			}
			
		} catch (Exception e) {
			flag = 0;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}
	

	//돈 움직이기(팀원들 pb 0으로 만듦)
		@PostMapping("/money/{subscribe_num}")
		public void minusMoney(@PathVariable("subscribe_num") int subscribe_num) {
			service.minusMoney(subscribe_num);
		}
		
	
	
	//subscribe_num으로 검색 (파티 전체 검색)
	@GetMapping("/party/{subscribe_num}")
	public Map getBySubNum(@PathVariable("subscribe_num") int subscribe_num) {
		ArrayList<SubscribePartyDto> list = service.getBySubNum(subscribe_num);
		System.out.println(list);
		Map map = new HashMap();
		boolean flag=false;
		
		if(list.size()> 1) {
			flag = false; 
		}else {
			flag = true;
		}
		map.put("list", list);
		map.put("flag", flag);
//		map.put("cnt", count(list.size()));
		
		return map;
	}
	
	//email 로 검색
	@PostMapping("/{email}")
	public Map getByEmail(@PathVariable("email") String email) {
		System.out.println(email);
		ArrayList<SubscribePartyDto> list = service.getByEmail(email);
		System.out.println(list);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	
	//pk로 검색
	@GetMapping("/{party}")
	public Map getDetail(@PathVariable("party") int party) {
		SubscribePartyDto dto = service.getParty(party);
		Map map = new HashMap();
		map.put("dto", dto);
		return map;
	}
	
	
	//email이랑 subnum으로 검색 (이미 참여했는지 아닌지 확인)
//	@GetMapping("/{subscribe_num}/{email}")
//	public Map getAddCheck(@PathVariable("subscribe_num") int subscribe_num, @PathVariable("email") String email) {
//		SubscribePartyDto dto = service.getByEmailSubnum(subscribe_num, email);
//		System.out.println(dto);
//		Map map = new HashMap();
//		map.put("dto", dto);
//		return map;
//	}
}
