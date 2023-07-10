package com.example.demo.naver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.demo.auth.JwtTokenProvider;
import com.example.demo.groupparty.GroupPartyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class content {
	public static String calSum =  "";
	public static String calDes = "";
	public static String calStart = "";
	public static String calEnd = "";
	public static String calStartTime = "";
	public static String calEndTime = "";
	public static String calEmail = "";
	public static String calGroup = "";

}

@Service
public class NaverService {
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private GroupPartyService groupservice;
	
	
	
	
	
	public String createNaverURL () throws UnsupportedEncodingException {
	    StringBuffer url = new StringBuffer();

	    // 카카오 API 명세에 맞춰서 작성
	    String redirectURI = URLEncoder.encode("http://www.localhost:8182/calendar", "UTF-8"); // redirectURI 설정 부분
	    SecureRandom random = new SecureRandom();
	    String state = new BigInteger(130, random).toString();

	    url.append("https://nid.naver.com/oauth2.0/authorize?response_type=code");
	    url.append("&client_id=" + "IiiFJKBOyzL3qvfXasPq");
	    url.append("&state=" + state);
	    url.append("&redirect_uri=" + redirectURI);

	  
	    return url.toString();
	}
	public String tokenNaver (String code, String state) throws JsonProcessingException {
	    // 네이버 로그인 Token 발급 API 요청을 위한 header/parameters 설정 부분
	    RestTemplate token_rt = new RestTemplate(); // REST API 요청용 Template

	    HttpHeaders naverTokenRequestHeadres = new HttpHeaders();  // Http 요청을 위한 헤더 생성
	    naverTokenRequestHeadres.add("Content-type", "application/x-www-form-urlencoded"); // application/json 했다가 grant_type missing 오류남 (출력포맷만 json이라는 거엿음)

	    // 파라미터들을 담아주기위한 맵 
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "authorization_code");
	    params.add("client_id", "IiiFJKBOyzL3qvfXasPq");
	    params.add("client_secret", "PtvyRoMmt_");
	    params.add("code", code);
	    params.add("state", state);

	    HttpEntity<MultiValueMap<String, String>> naverTokenRequest =
	            new HttpEntity<>(params, naverTokenRequestHeadres);

	    // 서비스 서버에서 네이버 인증 서버로 요청 전송(POST 또는 GET이라고 공식문서에 있음), 응답은 Json으로 제공됨
	    ResponseEntity<String> oauthTokenResponse = token_rt.exchange(
	            "https://nid.naver.com/oauth2.0/token",
	            HttpMethod.POST,
	            naverTokenRequest,
	            String.class
	    );

	    // body로 access_token, refresh_token, ;token_type:bearer, expires_in:3600 온 상태
	    System.out.println(oauthTokenResponse);

	    // oauthTokenResponse로 받은 토큰정보 객체화
	    ObjectMapper token_om = new ObjectMapper();
	    NaverTokenVo naverToken = null;
	    try {
	        naverToken = token_om.readValue(oauthTokenResponse.getBody(), NaverTokenVo.class);
	    } catch (JsonMappingException je) {
	        je.printStackTrace();
	    }
	    
	     
	    return naverToken.getAccess_token();
	}
	
	// 토큰 받아오기
	public Map userInfo(String access_token) throws JsonProcessingException {
		 // 토큰을 이용해 정보를 받아올 API 요청을 보낼 로직 작성하기
	    RestTemplate profile_rt = new RestTemplate();
	    HttpHeaders userDetailReqHeaders = new HttpHeaders();
	    userDetailReqHeaders.add("Authorization", "Bearer " + access_token);
	    userDetailReqHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	    HttpEntity<MultiValueMap<String, String>> naverProfileRequest = new HttpEntity<>(userDetailReqHeaders);

	    // 서비스서버 - 네이버 인증서버 : 유저 정보 받아오는 API 요청
	    ResponseEntity<String> userDetailResponse = profile_rt.exchange(
	            "https://openapi.naver.com/v1/nid/me",
	            HttpMethod.POST,
	            naverProfileRequest,
	            String.class
	    );

	    // 요청 응답 확인
	    System.out.println(userDetailResponse);

	    // 네이버로부터 받은 정보를 객체화
	    // *이때, 공식문서에는 응답 파라미터에 mobile 밖에없지만, 국제전화 표기로 된 mobile_e164도 같이 옴. 따라서 NaverProfileVo에 mobile_e164 필드도 있어야 정상적으로 객체가 생성됨
	    ObjectMapper profile_om = new ObjectMapper();
	    NaverProfileVo naverProfile = null;
	    try {
	        naverProfile = profile_om.readValue(userDetailResponse.getBody(), NaverProfileVo.class);
	    } catch (JsonMappingException je) {
	        je.printStackTrace();
	    }
	    Map map = new HashMap<>();
	    map.put("naverProfile", naverProfile);
	    return map;
	    
	}
	
	// 통합 code ,state 받아서 access_token -> 유저 인포도 나옴 
	public String loginNaver (String code, String state) throws JsonProcessingException {
	    // 네이버 로그인 Token 발급 API 요청을 위한 header/parameters 설정 부분
	    RestTemplate token_rt = new RestTemplate(); // REST API 요청용 Template

	    HttpHeaders naverTokenRequestHeadres = new HttpHeaders();  // Http 요청을 위한 헤더 생성
	    naverTokenRequestHeadres.add("Content-type", "application/x-www-form-urlencoded"); // application/json 했다가 grant_type missing 오류남 (출력포맷만 json이라는 거엿음)

	    // 파라미터들을 담아주기위한 맵 
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "authorization_code");
	    params.add("client_id", "IiiFJKBOyzL3qvfXasPq");
	    params.add("client_secret", "PtvyRoMmt_");
	    params.add("code", code);
	    params.add("state", state);

	    HttpEntity<MultiValueMap<String, String>> naverTokenRequest =
	            new HttpEntity<>(params, naverTokenRequestHeadres);

	    // 서비스 서버에서 네이버 인증 서버로 요청 전송(POST 또는 GET이라고 공식문서에 있음), 응답은 Json으로 제공됨
	    ResponseEntity<String> oauthTokenResponse = token_rt.exchange(
	            "https://nid.naver.com/oauth2.0/token",
	            HttpMethod.POST,
	            naverTokenRequest,
	            String.class
	    );

	    // body로 access_token, refresh_token, ;token_type:bearer, expires_in:3600 온 상태
	    System.out.println(oauthTokenResponse);

	    // oauthTokenResponse로 받은 토큰정보 객체화
	    ObjectMapper token_om = new ObjectMapper();
	    NaverTokenVo naverToken = null;
	    try {
	        naverToken = token_om.readValue(oauthTokenResponse.getBody(), NaverTokenVo.class);
	    } catch (JsonMappingException je) {
	        je.printStackTrace();
	    }
	    
	    // 토큰을 이용해 정보를 받아올 API 요청을 보낼 로직 작성하기
	    RestTemplate profile_rt = new RestTemplate();
	    HttpHeaders userDetailReqHeaders = new HttpHeaders();
	    userDetailReqHeaders.add("Authorization", "Bearer " + naverToken.getAccess_token());
	    userDetailReqHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	    HttpEntity<MultiValueMap<String, String>> naverProfileRequest = new HttpEntity<>(userDetailReqHeaders);

	    // 서비스서버 - 네이버 인증서버 : 유저 정보 받아오는 API 요청
	    ResponseEntity<String> userDetailResponse = profile_rt.exchange(
	            "https://openapi.naver.com/v1/nid/me",
	            HttpMethod.POST,
	            naverProfileRequest,
	            String.class
	    );

	    // 요청 응답 확인
	    System.out.println(userDetailResponse);

	    // 네이버로부터 받은 정보를 객체화
	    // *이때, 공식문서에는 응답 파라미터에 mobile 밖에없지만, 국제전화 표기로 된 mobile_e164도 같이 옴. 따라서 NaverProfileVo에 mobile_e164 필드도 있어야 정상적으로 객체가 생성됨
	    ObjectMapper profile_om = new ObjectMapper();
	    NaverProfileVo naverProfile = null;
	    try {
	        naverProfile = profile_om.readValue(userDetailResponse.getBody(), NaverProfileVo.class);
	    } catch (JsonMappingException je) {
	        je.printStackTrace();
	    }
	    
	   
	    
	    // 받아온 정보로 서비스 로직에 적용하기
	  //  Member naverMember = memberService.createNaverMember(naverProfile, naverToken.getAccess_token());

	    // 시큐리티 영역
	    // Authentication 을 Security Context Holder 에 저장
	   // Authentication authentication = new UsernamePasswordAuthenticationToken(naverMember.getEmail(), naverMember.getPassword());
	    //SecurityContextHolder.getContext().setAuthentication(authentication);

	    // 자체 JWT 생성 및 HttpServletResponse 의 Header 에 저장 (클라이언트 응답용)
	    //String accessToken = jwtTokenizer.delegateAccessToken(naverMember);
	    //String refreshToken = jwtTokenizer.delegateRefreshToken(naverMember);
	    //response.setHeader("Authorization", "Bearer " + accessToken);
	    //response.setHeader("RefreshToken", refreshToken);

	    // RefreshToken을 Redis에 넣어주는 과정
	    //ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
	   // valueOperations.set("RTKey"+naverMember.getMemberId(), refreshToken);
	    
	    System.out.println(naverToken.getAccess_token());
	   // scheduleadd(naverToken.getAccess_token());
	    
	    return naverToken.getAccess_token();
	}
	
	
	
	public String scheduleadd(String access_token) {
//		  System.out.println(dto.toString());
		  String result = "";
		  String token = access_token;
	        String header = "Bearer " + token; // Bearer 다음에 공백 추가
	        try {
	            String apiURL = "https://openapi.naver.com/calendar/createSchedule.json";
	            URL url = new URL(apiURL);
	            HttpURLConnection con = (HttpURLConnection)url.openConnection();
	            con.setRequestMethod("POST");
	            con.setRequestProperty("Authorization", header);
	        	
	            String calLoc =  URLEncoder.encode("[장소] 그린팩토리", "UTF-8");
	            String uid = UUID.randomUUID().toString();
	            String scheduleIcalString = "BEGIN:VCALENDAR\n" +
	                    "VERSION:2.0\n" +
	                    "PRODID:Naver Calendar\n" +
	                    "CALSCALE:GREGORIAN\n" +
	                    "BEGIN:VTIMEZONE\n" +
	                    "TZID:Asia/Seoul\n" +
	                    "BEGIN:STANDARD\n" +
	                    "DTSTART:19700101T000000\n" +
	                    "TZNAME:GMT%2B09:00\n" +
	                    "TZOFFSETFROM:%2B0900\n" +
	                    "TZOFFSETTO:%2B0900\n" +
	                    "END:STANDARD\n" +
	                    "END:VTIMEZONE\n" +
	                    "BEGIN:VEVENT\n" +
	                    "SEQUENCE:0\n" +
	                    "CLASS:PUBLIC\n" +
	                    "TRANSP:OPAQUE\n" +
	                    "UID:" + uid + "\n" +                          // 일정 고유 아이디
	                    "DTSTART;TZID=Asia/Seoul:" + content.calStart + "T"+ content.calStartTime + "\n" +  // 시작 일시
	                    "DTEND;TZID=Asia/Seoul:"+ content.calEnd + "T" + content.calEndTime +"\n" +    // 종료 일시
	                    "SUMMARY:"+ content.calSum +" \n" +                    // 일정 제목
	                    "DESCRIPTION:"+ content.calDes +" \n" +                // 일정 상세 내용
	                    "LOCATION:"+ calLoc +" \n" +                   // 장소
	                   // "RRULE:FREQ=YEARLY;BYDAY=FR;INTERVAL=1;UNTIL=20231231\n" +  // 일정 반복시 설정
	                    "ORGANIZER;CN=관리자:mailto:" + content.calEmail +" \n" + // 일정 만든 사람
	                    "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;CN=admin:mailto:"+content.calGroup+"\n" + // 참석자
	                    "CREATED:20230702T160000Z\n" +         // 일정 생성시각
	                    "LAST-MODIFIED:20230702T160000Z\n" +   // 일정 수정시각
	                    "DTSTAMP:20230702T160000Z\n" +         // 일정 타임스탬프
	                    "END:VEVENT\n" +
	                    "END:VCALENDAR";
	            String postParams = "calendarId=defaultCalendarId&scheduleIcalString=" + scheduleIcalString;
	            System.out.println("naver add start" + postParams + "naver add end ");
	            con.setDoOutput(true);
	            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	            wr.writeBytes(postParams);
	            wr.flush();
	            wr.close();
	            int responseCode = con.getResponseCode();
	            BufferedReader br;
	            if(responseCode==200) { // 정상 호출
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            } else {  // 에러 발생
	                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            }
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }
	            br.close();
	            System.out.println(response.toString());
	            result = response.toString();
	        } catch (Exception e) {
	        	System.out.println(e);
	            result = e.toString();
	        }
	        return result;
	}
	
	

	
}
