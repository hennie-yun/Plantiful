package com.example.demo.naver;


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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.schedule.ScheduleDto;
import com.example.demo.schedule.ScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;

@CrossOrigin(origins = "*")
@RequestMapping("/api/naver")
@RestController
public class NaverController {
	
	@Autowired
	private NaverService naverService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	
	private String CLIENT_ID = "IiiFJKBOyzL3qvfXasPq";
	private String CLIENT_SECRET = "PtvyRoMmt_";
	private String state = "";
	private String access_token="";
	

	
	@GetMapping("/oauth")
	public ResponseEntity<?> naverConnect() throws UnsupportedEncodingException {
	    String url = naverService.createNaverURL();

	    return new ResponseEntity<>(url, HttpStatus.OK); // 프론트 브라우저로 보내는 주소
	}
	
	@PostMapping("/login")
	public Map naverToken(Code code) throws JsonProcessingException {
	
		System.out.println("code" + code.getCode());
		System.out.println("state" + code.getState());
		
		
		
		access_token = naverService.loginNaver(code.getCode(), code.getState());
		Map map = new HashMap<>();
		map.put("access_token", access_token);
		map.put("userinfo", naverService.userInfo(access_token));
		//map.put("naveruserinfo", naverService.userInfo(access_token));
		return map;
	}

	


	
	// 네이버 로그인 연동 안되어 있을때 버전 로그인해서 받은 code, state 값으로 -> access_token 받아옴 -> 바로 네이버 일정 추가
	@GetMapping("/callback")
	public ModelAndView naverMerge(@RequestParam("code") String code, @RequestParam("state") String state) throws JsonProcessingException {
		
		System.out.println("code"+code);
		System.out.println("state"+state);
		
		
		access_token =  naverService.loginNaver(code, state);
	    System.out.println("callback");
	    System.out.println("access_token"+access_token);
	    String result = naverService.scheduleadd(access_token);
	
	    // 받아온 정보로 서비스 로직에 적용하기
       // Member naverMember = memberService.createNaverMember(naverProfile, naverToken.getAccess_token());

        // 시큐리티 영역
        // Authentication 을 Security Context Holder 에 저장
        //Authentication authentication = new UsernamePasswordAuthenticationToken(naverMember.getEmail(), naverMember.getPassword());
        //SecurityContextHolder.getContext().setAuthentication(authentication);

        // 자체 JWT 생성 및 HttpServletResponse 의 Header 에 저장 (클라이언트 응답용)
        //String accessToken = jwtTokenizer.delegateAccessToken(naverMember);
        //String refreshToken = jwtTokenizer.delegateRefreshToken(naverMember);
        //response.setHeader("Authorization", "Bearer " + accessToken);
        //response.setHeader("RefreshToken", refreshToken);
//	    Map map = new HashMap();
//	    map.put("result", result);
//        return map; 

	    /* Header가 아닌 Redis 서버에 잘 저장이 되었는지 확인하기 */
	   // return response.getHeader("Authorization") == null ? "Fail Login: User" :  "Success Login: User";
	    
	    String redirectURL = null;
		try {
			redirectURL = "http://localhost:8182/calendar?result="+URLEncoder.encode(result, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return new ModelAndView("redirect:"+redirectURL);
	}
	
	@PostMapping("/calendar")
	public Map calendar(ScheduleDto dto) {
		
		String email = dto.getEmail().getEmail();
	    System.out.println(dto.toString());

		 try {
			content.calSum =  URLEncoder.encode("[plantiful] " + dto.getTitle(), "UTF-8");
			content.calDes =  URLEncoder.encode("[plantiful] " + dto.getInfo(), "UTF-8");
			content.calStart = URLEncoder.encode(dto.getStart().replace("-", ""), "UTF-8");
			content.calEnd = URLEncoder.encode(dto.getEnd().replace("-", ""), "UTF-8");
			content.calStartTime = URLEncoder.encode(dto.getStartTime().replace(":", "")+"00", "UTF-8");
			content.calEndTime = URLEncoder.encode(dto.getEndTime().replace(":", "")+"00", "UTF-8");
			content.calEmail = URLEncoder.encode(email, "UTF-8");
			content.calGroup = URLEncoder.encode(scheduleService.getMemberEmail(dto), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		ArrayList<Member> list = groupservice.getGroupMemberByScheduleNum(dto.getSchedule_num());
//		String member="";
//		for(Member m : list) {
//			member += m;
//		}
//		content.calGroup = URLEncoder.encode(member, "UTF-8");
		 
	System.out.println("캘린더 add>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");	 
	System.out.println(content.calSum);
	System.out.println(content.calDes);
	System.out.println(content.calStart);
	System.out.println(content.calEnd);
	System.out.println(content.calStartTime);
	System.out.println(content.calEndTime);
	System.out.println(content.calEmail);
	System.out.println(content.calGroup);
	Map map = new HashMap();
	map.put("dto", dto.toString());
	// 값이 받아와지면 저장되어야 할 곳...에 저장 
//	    return naverService.scheduleadd(access_token);
		return map;
	//	return dto
	}
	
	
	
	
	
	

	
} 