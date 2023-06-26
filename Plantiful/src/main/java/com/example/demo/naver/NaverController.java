package com.example.demo.naver;




import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/naver")
public class NaverController {
	private String naver_client = "IiiFJKBOyzL3qvfXasPq";
	private String naver_secret = "PtvyRoMmt_";
	
//	public static String generateState()
//    {
//        SecureRandom random = new SecureRandom();
//        return new BigInteger(130, random).toString(32);
//    }
	@RequestMapping(value="/api/naver/callback")
	public void NaverLogin(){
		System.out.println("여기까지는 왔다");
	
				
				
	}
	
	@GetMapping("/accesstoken")
	public Map addSchedule(@RequestHeader("access_token") String access_token) {
		System.out.println(access_token);
		boolean flag = false;
		Map map = new HashMap();
		String token = "AAAAOuvY0VoIgbvTlgtFjfku2MfS3/sLHR4pv5obGLMBxd4fSziNG+i7XGDxYL4Ez71cDELzUzK/tIqCOz3kghRTi4w=";
		String header = "Bearer " + token;
		try {
			String apiURL = "https://openapi.naver.com/calendar/createSchedule.json";
			URL url = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", header);
            String calSum = URLEncoder.encode("[제목] 캘린더 api로 추가한 일정", "UTF-8");
			String calDes = URLEncoder.encode("[상세] 집에 가고싶다!!", "UTF-8");
			String calLoc = URLEncoder.encode("[장소] 소프프트웨어 진흥협회", "UTF-8");
			String uid = UUID.randomUUID().toString();
			String scheduleIcalString = "BEGIN:VCALENDAR\n" +  "VERSION:2.0\n" +
                    "PRODID:Naver Calendar\n" +
                    "CALSCALE:GREGORIAN\n" +
                    "BEGIN:VTIMEZONE\n" +
                    "TZID:Asia/Seoul\n" +
                    "BEGIN:STANDARD\n" +
                    "DTSTART:20230626T000000\n" +
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
                    "DTSTART;TZID=Asia/Seoul:20230626T170000\n" +  // 시작 일시
                    "DTEND;TZID=Asia/Seoul:20230626T173000\n" +    // 종료 일시
                    "SUMMARY:"+ calSum +" \n" +                    // 일정 제목
                    "DESCRIPTION:"+ calDes +" \n" +                // 일정 상세 내용
                    "LOCATION:"+ calLoc +" \n" +                   // 장소
                    "RRULE:FREQ=YEARLY;BYDAY=FR;INTERVAL=1;UNTIL=20231231\n" +  // 일정 반복시 설정
                    "ORGANIZER;CN=관리자:mailto:rmsgml7553@naver.com\n" + // 일정 만든 사람
                    "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;CN=admin:mailto:rmsgml7553@naver.com\n" + // 참석자
                    "CREATED:20230623T160000Z\n" +         // 일정 생성시각
                    "LAST-MODIFIED:20230623T160000Z\n" +   // 일정 수정시각
                    "DTSTAMP:20230626T160000Z\n" +         // 일정 타임스탬프
                    "END:VEVENT\n" +
                    "END:VCALENDAR";
			String postParams = "calendarId=rmsgml7553.defaultCalendarId&scheduleIcalString="+scheduleIcalString;
			System.out.println(postParams);
			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
			int responseCode = conn.getResponseCode();
			BufferedReader br;
			if(responseCode==200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				flag = true;
				System.out.println("here");
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			String inputLine;
			StringBuffer response =new StringBuffer();
			while((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			System.out.println(response.toString());
			map.put("flag", flag);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return map;
		
	}
	
	
	
	
	
	
//	@RequestMapping(value = "/api/naver/callback")
//	public void NaverLogin(@RequestParam("code") String code, @RequestParam("state") String state){
//	        RestTemplate rt = new RestTemplate();
//	        HttpHeaders httpHeaders = new HttpHeaders();
//	        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//	        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
//	        params.add("client_id", naver_client);
//	        params.add("client_secret", naver_secret);
//	        params.add("grant_type", "authorization_code");
//	        params.add("state", state);  // state 일치를 확인
//	        params.add("code", code);
//
//	        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = new HttpEntity<>(params,httpHeaders);
//
//	        ResponseEntity<String> response = rt.exchange(
//	                "https://nid.naver.com/oauth2.0/token",
//	                HttpMethod.POST,
//	                kakaoTokenRequest,
//	                String.class
//	        );
//
//	        // 토큰값 Json 형식으로 가져오기위해 생성
//	        JSONObject jo = new JSONObject();
//	        jo.put("access_token", response.getBody());
//	        
//	        // 토큰결과값
//	        System.out.println("naver Id token result = {} " +  response);
//
//	        RestTemplate rt2 = new RestTemplate();
//	        HttpHeaders headers2 = new HttpHeaders();
//
//	        headers2.add("Authorization", "Bearer "+ jo.get("access_token"));
//	        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
//
//	        HttpEntity<MultiValueMap<String,String >> kakaoProfileRequest2= new HttpEntity<>(headers2);
//
//	        ResponseEntity<String> response2 = rt2.exchange(
//	                "https://openapi.naver.com/v1/nid/me",
//	                HttpMethod.POST,
//	                kakaoProfileRequest2,
//	                String.class
//	        );
//
//
//		}
	        
	} 
