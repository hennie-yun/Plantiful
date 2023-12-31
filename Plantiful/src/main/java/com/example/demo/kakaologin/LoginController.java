package com.example.demo.kakaologin;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.kakao.KakaoController;
import com.example.demo.member.MemberService;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	private MemberService memberservice;

	@Autowired
	private LoginService loginService;

	@Autowired
	private KakaoController KakaoController;

	// 코드를 빼와서 access token 을 얻어다 줄 것임
	@GetMapping("/kakaologin/{code}")
	public HashMap<String, String> kakaoLogin(@PathVariable("code") String code) {
		System.out.println(code);
		// 토큰을 요청하여 얻음
		String kakaoToken = loginService.requestToken(code);
		System.out.println("카카오토큰" + kakaoToken);
		// 사용자 정보를 요청하여 얻음
		HashMap<String, String> userInfo = loginService.requestUser(kakaoToken);
		System.out.println("userInfo : " + userInfo);

		// 토큰을 아이디와 함께 저장할 것임 혹시 같은 아이디가 있다면 토큰 정보만 업데이트 할 것

		return userInfo;
	}

}
