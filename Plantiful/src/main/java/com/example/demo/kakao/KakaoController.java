package com.example.demo.kakao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.JwtTokenProvider;
import com.example.demo.kakaoaccesstoken.Kakaotoken;
import com.example.demo.kakaoaccesstoken.KakaotokenService;
import com.example.demo.schedule.ScheduleDto;

@RestController
@CrossOrigin("*")
public class KakaoController {
	
	@Autowired
	private KakaoService kakaoService;
	
	@Autowired
	private KakaotokenService kakaotokenService;
	
	@Autowired(required = false)
	private JwtTokenProvider tokenprovider;
	
	private String access_token="";
	
	@PostMapping("/api/kakao/form")
	public Map getKakaoData(ScheduleDto dto) {
		System.out.println(dto.toString());
		ScheduleDto savedto = new ScheduleDto();
		System.out.println(dto.getStartTime());
		
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
	public Map getKakaotoken(@RequestHeader(value = "token", required = false) String token) {
		Map map = new HashMap();
		if(token != null) {
			try {
				String email = tokenprovider.getUsernameFromToken(token); 
				System.out.println("token받은것 이메일"+email);
				Kakaotoken kakaotoken = kakaotokenService.findByEmail(email);
				access_token = kakaotoken.getToken();
				String result = kakaoService.scheduleadd(access_token);
				map.put("result", result);
			} catch (Exception e) {
				// TODO: handle exception
				map.put("kakaotoken", e);
			}
		}
		return map;
	}
	

	

}
