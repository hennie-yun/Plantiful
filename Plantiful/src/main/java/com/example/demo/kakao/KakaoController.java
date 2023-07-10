package com.example.demo.kakao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.JwtTokenProvider;
import com.example.demo.kakaoaccesstoken.Kakaotoken;
import com.example.demo.kakaoaccesstoken.KakaotokenService;
import com.example.demo.kakaologin.KakaoToken;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;
import com.example.demo.schedule.ScheduleDto;

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

	 
	private static LocalTime roundToNearestFiveMinutes(LocalTime time) {
        int minute = time.getMinute();
        int roundedMinute = ((minute + 4) / 5) * 5;
        return LocalTime.of(time.getHour(), roundedMinute);
	}
	
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
//		String startAt = dto.getStart()+"T"+dto.getStartTime()+"00";
//		String endAt = dto.getEnd()+"T"+dto.getEndTime()+"00";
//		
//		LocalDateTime dateTime = LocalDateTime.parse(startAt);
//		LocalDateTime dateTime2 = LocalDateTime.parse(endAt);
//		
//		LocalTime adjustedTime = roundToNearestFiveMinutes(dateTime.toLocalTime());
//		LocalTime adjustedTime2 = roundToNearestFiveMinutes(dateTime2.toLocalTime());
//		
//		LocalDateTime adjustedDateTime = LocalDateTime.of(dateTime.toLocalDate(), adjustedTime);
//		LocalDateTime adjustedDateTime2 = LocalDateTime.of(dateTime2.toLocalDate(), adjustedTime2);
//		
//		String startTimeAt = adjustedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//		String startTimeAt2 = adjustedDateTime2.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		
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
	public Map getKakaotoken(@RequestHeader(value = "authorization_code", required = false) String authorization_code) {
		String result = "";
		String kakao = "";
		System.out.println("kakao token");
		System.out.println(authorization_code);
		Map map = new HashMap();
			System.out.println("kakao token");
			System.out.println(authorization_code);
			if(authorization_code != null) {
				System.out.println(authorization_code);
				result = kakaoService.authToken(authorization_code);
				System.out.println("new access_token"+result);
				access_token = result;
				System.out.println("access_token" + access_token);
				kakao = kakaoService.scheduleadd(access_token);
				System.out.println("kakao****"+kakao);
			}
			System.out.println("scheduleadd result:"+kakao);	
			map.put("result", result);
			map.put("kakao", kakao);
			return map;
		}
		
		//System.out.println("scheduleadd result:"+map);	
	
	/*

	@GetMapping("/api/kakao/token")
	public Map getKakaotoken(@RequestHeader(value = "authorization_code", required = false) String authorization_code) {
		String result = "";
		String kakao = "";
		System.out.println("kakao token");
		System.out.println(authorization_code);
		Map map = new HashMap();
		if(authorization_code != " ") {
			System.out.println(authorization_code);
			result = kakaoService.authToken(authorization_code);
			System.out.println("new access_token"+result);
			access_token = result;
			System.out.println("access_token" + access_token);
			kakao = kakaoService.scheduleadd(access_token);
			System.out.println("kakao****"+kakao);
		}
		//System.out.println("scheduleadd result:"+map);	
		map.put("result", result);
		//map.put("kakao", kakao);
		return map;
	}
	*/
	

	@GetMapping("/api/kakao/oauth")
	public Map getOauth(@RequestHeader(value="authorization_code") String authorization_code) {
		access_token = kakaoService.authToken(authorization_code);
	
		
		Map map = new HashMap();
		map.put("access_token", access_token);
	//	map.put("result", result);
		return map;
		
	}
	

	@GetMapping("/api/kakao/member")
	public Map getMemberCheck(@RequestHeader(value="token") String token) {
		String result = "";
		System.out.println("member token" + token);
		String email = tokenprovider.getUsernameFromToken(token);
		System.out.println("kakaomember"+email);
		MemberDto member = memberService.getMember(email);
		long id = member.getId(); 
		System.out.println("memberdto"+member);
		Kakaotoken kakao = kakaotokenService.findByEmail(email);
		System.out.println("kakao" + kakao);
		Map map = new HashMap();
		access_token = kakao.getToken();
		if(id==1) {
			result = kakaoService.scheduleadd(access_token);
		}
		map.put("id", id);
		map.put("access_token", access_token);
		map.put("result", result);
		return map;
	}
	
	
	

	@GetMapping("/api/kakao/add")
	public Map addschedule() {
		System.out.println(">>> add access_token"+access_token);
		Map map = new HashMap();
		
		String result = kakaoService.scheduleadd(access_token);
		map.put("result", result);
		return map;
	}
	
	
	

}
