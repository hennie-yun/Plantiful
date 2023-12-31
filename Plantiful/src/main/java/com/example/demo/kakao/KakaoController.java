package com.example.demo.kakao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.auth.JwtTokenProvider;
import com.example.demo.kakaoaccesstoken.Kakaotoken;
import com.example.demo.kakaoaccesstoken.KakaotokenService;
import com.example.demo.kakaologin.KakaoToken;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;
import com.example.demo.schedule.ScheduleDto;
import com.example.demo.schedule.ScheduleService;

@RestController
@CrossOrigin("*")
public class KakaoController {
	
	@Autowired
	private KakaoService kakaoService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private KakaotokenService kakaotokenService;
	
	@Autowired(required = false) 
	private JwtTokenProvider tokenprovider;
	
	private String authorization_code="";
	private String access_token="";
	private String email = "";
	 

	@PostMapping("/api/kakao/form")
	public Map getKakaoData(ScheduleDto dto) {
		System.out.println(dto.toString());
		ScheduleDto savedto = new ScheduleDto();
		
		System.out.println(dto.getStartTime());
		if(dto.getStartTime() == null) {
			dto.setStartTime("00:00:00z");
		} 
		if (dto.getEndTime() == null) {
			dto.setEndTime("00:00:00z");
		}
		
		try {	
			data.schedule_num = dto.getSchedule_num();
			data.group_num = dto.getGroup_num();
			data.email = dto.getEmail();
			data.title = URLEncoder.encode(dto.getTitle(), "UTF-8");
			data.start = URLEncoder.encode(dto.getStart(), "UTF-8");
			data.end = URLEncoder.encode(dto.getEnd(), "UTF-8");
			data.info = URLEncoder.encode(dto.getInfo(), "UTF-8");
			data.alert = URLEncoder.encode(dto.getAlert(), "UTF-8");
			data.isLoop = dto.getIsLoop();
			data.day =URLEncoder.encode(dto.getDay(), "UTF-8");
			data.startTime = URLEncoder.encode(dto.getStartTime(), "UTF-8");
			data.endTime = URLEncoder.encode(dto.getEndTime(), "UTF-8");
			//data.startTime = dto.getStartTime().split(":")[0]+":"+ dto.getStartTime().split(":")[1]+":00z";
			//data.endTidme =  dto.getEndTime().split(":")[0]+":"+dto.getEndTime().split(":")[1]+":00z";
			savedto = new ScheduleDto(
					data.schedule_num, data.group_num,
					data.email, data.title,
					data.start, data.end,
					data.startTime ,data.endTime,
					data.info, data.alert,
					data.isLoop, data.day);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println(">>>>>>>>>>>>>>>>>");
		System.out.println(data.schedule_num);
		System.out.println(data.group_num);
		System.out.println(data.email);
		System.out.println(data.title);
		System.out.println(data.start);
		System.out.println(data.end);
		System.out.println(data.startTime);
		System.out.println(data.endTime);
		System.out.println(data.info);
		System.out.println(data.alert);
		System.out.println(data.isLoop);
		System.out.println(data.day);
		
		
		Map map = new HashMap();
		map.put("dto", savedto);
		return map;
	}

	@GetMapping("/api/kakao/token")
	public ModelAndView getKakaotoken(@RequestParam("code") String code) throws UnsupportedEncodingException {
		String result = "";
		String kakao = "";
		String redirectURL = null;
		
		System.out.println("kakao token");
		System.out.println(code);
		
			if(code != null) {
				// result에 access_token 담아옴
				result = kakaoService.tokenKakao(code);
				System.out.println("new access_token"+result);
				access_token = result;
				System.out.println("새로받아온 토큰"+access_token);
				// 담아온 토큰을 email 토큰에 저장
				Kakaotoken token = kakaotokenService.findByEmail(email);
				token.setToken(access_token);
				System.out.println(token);
				kakaotokenService.edit(token);
				System.out.println("access_token" + token);
				kakao = kakaoService.scheduleadd(access_token);
				
				redirectURL = "http://localhost:8182/calendar?result="+URLEncoder.encode(kakao, "UTF-8");
				
			}
			System.out.println("scheduleadd result:"+kakao);	
			return new ModelAndView("redirect:"+redirectURL);
		}
		
	
	@GetMapping("/api/kakao/gettoken")
	public Map token(@RequestHeader(value="token") String token) {
		boolean result;
		Map map = new HashMap<>();
		String email = tokenprovider.getUsernameFromToken(token);
		Kakaotoken kakaotoken= kakaotokenService.findByEmail(email);
		String accessToken = kakaotoken.getToken();
		map.put("accessToken", accessToken);
		return map;
	}
	
	@GetMapping("/api/kakao/tokencheck")
	public Map tokenCheck(@RequestHeader(value="token") String token) {
		boolean result;
		Map map = new HashMap<>();
		email = tokenprovider.getUsernameFromToken(token);
		Kakaotoken kakaotoken= kakaotokenService.findByEmail(email);
		try {
			System.out.println("kakaotoken" + kakaotoken.getToken());
			result = kakaoService.checkToken(kakaotoken.getToken());
			map.put("result", result);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	
	@GetMapping("/api/kakao/oauth")
	public ResponseEntity<?> kakaoConnect() throws UnsupportedEncodingException{
		String url = kakaoService.createKakaoURL();
		return new ResponseEntity<>(url, HttpStatus.OK);
	}

	@GetMapping("/api/kakao/callback")
	public ModelAndView kakaoMerge(@RequestParam("code") String code) {
		System.out.println("code"+code);
		
		access_token = kakaoService.tokenKakao(code);
		System.out.println("callback");
		System.out.println("access_token"+access_token);
		Kakaotoken token = kakaotokenService.findByEmail(email);
		token.setToken(access_token);
		//String result = kakaoService.scheduleadd(access_token);
		String redirectURL = null;
		try {
			redirectURL = "http://localhost:8182/calendar?result="+URLEncoder.encode(token.getToken(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:"+redirectURL);
		
	}
	
	
	@GetMapping("/api/kakao/member")
	public Map getMemberCheck(@RequestHeader(value="token") String token) {
		String result = "";
		System.out.println("member token" + token);
		String email = tokenprovider.getUsernameFromToken(token);
		System.out.println("kakaomember"+email);
		MemberDto member = memberService.getMember(email);
		// 일반 : 0, 카카오 : 1, 네이버 : 2
		long id = member.getId(); 
//		Kakaotoken kakao =  kakaotokenService.findByEmail(email);
//		System.out.println("original // access_token"+kakao.getToken());
		
		Map map = new HashMap();
		map.put("id", id);
		return map;
	}
	

	
//	@GetMapping("/api/kakao/member")
//	public Map getMemberCheck(@RequestHeader(value="token") String token) {
//		String result = "";
//		System.out.println("member token" + token);
//		String email = tokenprovider.getUsernameFromToken(token);
//		System.out.println("kakaomember"+email);
//		MemberDto member = memberService.getMember(email);
//		long id = member.getId(); 
//		System.out.println("memberdto"+member.toString());
//		Map map = new HashMap();
//		map.put("id", id);
//		return map;
//	}
	
	

	@GetMapping("/api/kakao/add")
	public Map addschedule(@RequestHeader(value="access_token") String access_token) {
		System.out.println(">>> add access_token"+access_token);
		Map map = new HashMap();
		
		String result = kakaoService.scheduleadd(access_token);
		System.out.println("result" + result);
		map.put("result", result);
		return map;
	}
	
	
	

}
