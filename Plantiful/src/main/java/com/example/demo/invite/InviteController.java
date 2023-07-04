package com.example.demo.invite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.JwtTokenProvider;
import com.example.demo.groupparty.GroupPartyDto;
import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;

@RestController // rest api controller
@CrossOrigin(origins = "*") // 모든 ip로부터 요청 받기 허용
@RequestMapping("/invite") // 기본 url
public class InviteController {
	@Autowired
	private InviteService service;
	@Autowired
	private MemberService mservice;

	@Autowired
	private JwtTokenProvider tokenProvider;

	// 추가
	@PostMapping("")
	public Map add(InviteDto dto) {
		boolean flag = true;
		Map map = new HashMap();
		Member email = dto.getEmail();
		if (email == null) {
			map.put("flag", false);
		} else {
			InviteDto i = service.save(dto);
			map.put("dto", i);
		}
		return map;
	}

	// list 검색
	@GetMapping("")
	public Map getAll() {
		ArrayList<InviteDto> list = service.getall();
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}

	// email로 검색
	@GetMapping("email/{email}")
	public Map getByEmail(@PathVariable("email") String email,
			@RequestHeader(name = "token", required = false) String token) {
		Map map = new HashMap();
		if (token != null) {
			try {
				String id = tokenProvider.getUsernameFromToken(token);
				if (!email.equals(id)) {
					map.put("dto", null);
					return map;
				}
			} catch (Exception e) {
				map.put("dto", null);
				return map;
			}
		}
		ArrayList<InviteDto> list = service.getByEmail(email);
		System.out.println("내 초대" + list);
		map.put("list", list);
		return map;
	}

	// 초대 삭제
	@DeleteMapping("invite_num/{invitenum}")
	public Map delInvite(@PathVariable("invitenum") int invitenum) {
		boolean flag = true;
		try {
			service.delinvite(invitenum);
		} catch (Exception e) {
			flag = false;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}
}
