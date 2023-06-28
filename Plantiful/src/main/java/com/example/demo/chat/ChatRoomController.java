package com.example.demo.chat;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.auth.JwtTokenProvider;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;

@Controller
@CrossOrigin(origins = "*")
public class ChatRoomController {
	
	@Autowired
	private ChatRoomService service;
	
	@Autowired
	private MemberService memService;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	/**
	 * 채팅방을 접속한 유저의 이메일을 기준으로 찾기 
	 * @param email
	 * @return 
	 */
	@RequestMapping("/chat/roomlist")
	@ResponseBody
	public Map findRoomById(String email) {
		Map map = new HashMap();
		ArrayList<ChatRoomDto> list = service.findRoomById(email);
		map.put("list", list);
		return map;
	}
	
	/**
	 * 채팅방에 접속 시 채팅방의 방 번호를 기준으로 채팅 전체를 조회하여 반환
	 * @param roomNum
	 * @return
	 */
	@RequestMapping("chat/joinroom")
	@ResponseBody
	public Map joinRoom(long roomNum) {
		ArrayList<ChatDto> list = service.findChatByRoomNum(roomNum);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	/**
	 * email을 기준으로 프로필 사진 반환
	 * @param email
	 * @return
	 */
	@RequestMapping("chat/{email}")
	@ResponseBody
	public ResponseEntity<byte[]> getImg(@PathVariable(name = "eamil")String email) {
		MemberDto member = memService.getMember(email);
		String fname = member.getImg();
		File f = new File(fname);
		HttpHeaders header = new HttpHeaders();
		ResponseEntity<byte[]> result = null;
		try {
			header.add("Content-Type", Files.probeContentType(f.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(f), header, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
