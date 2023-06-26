package com.example.demo.kakaologin;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.member.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LoginController {

	@Autowired
	private MemberService memberservice;

	@Autowired
	private LoginService loginService;

	// 코드를 빼와서 access token 을 얻어다 줄 것임
	@GetMapping("/kakaologin")
	public HashMap<String, String> kakaoLogin(@RequestParam String code) {

		// 토큰을 요청하여 얻음
		String kakaoToken = loginService.requestToken(code);

		// 사용자 정보를 요청하여 얻음
		HashMap<String, String> userInfo = loginService.requestUser(kakaoToken);

		return userInfo;
	}

//    @GetMapping("/kakaologout")
//	public void logout(HttpSession session, @RequestHeader(name = "access-token", required = false) String accessToken) {
//    	System.out.println(session);
//    	System.out.println(accessToken);
//    	loginService.logout(accessToken);
//    	//loginService.logout((String)session.getAttribute("access_token"));
//		session.invalidate();
//		
//	}

//    @PostMapping("/kakaologout")
//    public void logout(@RequestHeader(name = "access-token") String accessToken) {
//        System.out.println(accessToken);
//        loginService.logout(accessToken);
//        // 세션 관련 로직 처리
//    }

}
