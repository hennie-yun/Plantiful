package com.example.demo.naver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.member.MemberService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/naver/token")
public class NaverMemberController {
	
	@Autowired
	private NaverMemberService naverservice;
	
	@Autowired
	private MemberService memberservice;
	
	//추가
	@PostMapping("")
	public void naverLogin(Navertoken navertoken) {
		String email = navertoken.getEmail();
		System.out.println("naver토큰 받는 이메일" + email);
		String token = navertoken.getToken();
		System.out.println("naver토큰의 토큰" + token);
		boolean flag = naverservice.findByEmail(email) != null;
		if(flag) {
			// 기존 정보가 있는 경우
			Navertoken existingtoken = naverservice.findByEmail(email);
			existingtoken.setToken(token);
			naverservice.edit(navertoken);
		}
	}
	
	// 이메일로 찾아서 네이버 토큰 자체를 삭제
	@DeleteMapping("/deltoken/{email}")
	public void naverdelete(@PathVariable("email") String email) {
		System.out.println("네이버 삭제"+ email);
		Navertoken navertoken = naverservice.findByEmail(email);
		System.out.println(navertoken.getToken());
		System.out.println(navertoken.getEmail());
		
		naverservice.delNavertoken(email);
		
		// 멤버테이블에서도 삭제
		memberservice.delMember(email);
	}
	
	
}
