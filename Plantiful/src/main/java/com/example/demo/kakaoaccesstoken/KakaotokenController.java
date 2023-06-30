package com.example.demo.kakaoaccesstoken;

import java.io.IOException;

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
@RequestMapping("/tokensave")
public class KakaotokenController {

	@Autowired
	private KakaotokenService service;

	@Autowired
	private MemberService memservice;

	// 추가
	@PostMapping("")
	public void kakaoLogin(Kakaotoken ktokenput) {
		String email = ktokenput.getEmail();
		System.out.println("카카오토큰받는 이메일" + email);
		String token = ktokenput.getToken();
		System.out.println("카카오토큰의 토큰" + token);
		boolean exists = service.findByEmail(email) != null;
		if (exists) {
			// 기존 정보가 있는 경우
			Kakaotoken existingToken = service.findByEmail(email);
			existingToken.setToken(token);
			service.edit(existingToken);
		} else {
			// 기존 정보가 없는 경우
			Kakaotoken ktoken = new Kakaotoken();
			ktoken.setToken(token);
			ktoken.setEmail(email);
			service.edit(ktoken);
		}
	}

	// 이메일로 찾아서 카카오톡 토큰 자체를 삭제
	@DeleteMapping("/deltoken/{email}")
	public void kakaodelete(@PathVariable("email") String email) {
		System.out.println("삭제까지 들어왔음" + email);
		Kakaotoken kakaoToken = service.findByEmail(email);
		System.out.println("삭제들어와서 토큰" + kakaoToken.getToken());
		System.out.println(kakaoToken.getEmail());

		service.outKakao(kakaoToken.getToken());
		service.delKtoken(email);
		memservice.delMember(email);

	}

}
