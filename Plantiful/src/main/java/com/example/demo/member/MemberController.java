package com.example.demo.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.JwtTokenProvider;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/members")
public class MemberController {

	@Autowired
	private MemberService service;

	@Autowired
	private JwtTokenProvider tokenprovider;
	// 회원가입/세션아이디발급/검색/내정보수정/삭제

	// 가입
	@PostMapping("")
	public Map join(MemberDto dto) {
		MemberDto d = service.save(dto);
		Map map = new HashMap();
		map.put("dto", d);
		return map;
	}

	//로그인
	@PostMapping("/login") 
	public Map login(String email, String pwd) {
		Map map = new HashMap();
		boolean flag = false;
		MemberDto dto = service.getMember(email);
		if (dto != null && pwd.equals(dto.getPwd())) {
			String token = tokenprovider.generateJwtToken(dto);
			flag = true;
			map.put("token", token);
		}
		map.put("flag", flag);
		return map;
	}


	
	@PostMapping("/token")
	public Map getByToken(String token) {
		boolean flag = true;
		Map map = new HashMap();
		try {
			// 받은 토큰에서 로그인한사람의 아이디, 타입 정보 추출
			String email = tokenprovider.getUsernameFromToken(token);
			map.put("email", email);
		} catch (Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		return map;
	}


	//토큰값으로 정보 불러오기
	@GetMapping("")
	public Map getInfo(@RequestHeader("token") String token) {
		boolean flag = true;
		Map map = new HashMap();
		try {
			String email = tokenprovider.getUsernameFromToken(token);
			map.put("email", email);
		} catch (Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		return map;
	}


	@GetMapping("/{email}")
	public Map get(@PathVariable("email") String email, @RequestHeader(name = "token", required = false) String token) {
		Map map = new HashMap();
		if (token != null) {// 로그인 후
			try {
				String id = tokenprovider.getUsernameFromToken(token);
				if (!email.equals(id)) {
					map.put("dto", null);
					return map;
				}
			} catch (Exception e) {
				// flag = false;
				map.put("dto", null);
				return map;
			}
		}
		MemberDto d = service.getMember(email);
		map.put("dto", d);
		return map;
	}

	@PutMapping("")
	public Map edit(MemberDto dto, @RequestHeader(name = "token", required = false) String token) {
		boolean flag = true;
		Map map = new HashMap();
		if (token != null) {// 로그인 후
			try {
				String id = tokenprovider.getUsernameFromToken(token);
				if (!id.equals(dto.getEmail())) {
					flag = false;
				}
			} catch (Exception e) {
				// flag = false;
				flag = false;
			}
		}
		if (flag) {
			MemberDto old = service.getMember(dto.getEmail());
			old.setPwd(dto.getPwd());
			old.setNickname(dto.getNickname());
			old.setPhone(dto.getPhone());
			MemberDto d = service.save(old);
			map.put("dto", d);
		}
		map.put("flag", flag);
		return map;
	}

	@DeleteMapping("/{email}")
	public Map del(@PathVariable("email") String email, @RequestHeader(name = "token", required = false) String token) {
		boolean flag = true;
		Map map = new HashMap();
		if (token != null) {
			try {
				String id = tokenprovider.getUsernameFromToken(token);
				if (!email.equals(id)) {
					flag = false;
				}
			} catch (Exception e) {
				// flag = false;
				flag = false;
			}
		}
		if (flag) {
			service.delMember(email);
		}
		map.put("flag", flag);
		return map;
	}
}
