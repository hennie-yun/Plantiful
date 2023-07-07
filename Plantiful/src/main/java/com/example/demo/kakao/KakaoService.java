package com.example.demo.kakao;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.kakaologin.KakaoToken;
import com.example.demo.member.Member;
import com.example.demo.schedulegroup.ScheduleGroup;
import com.fasterxml.jackson.databind.ObjectMapper;

class data{
	public static int schedule_num = 0;
	public static ScheduleGroup group_num = null;
	public static Member email = null;
	public static String title = "";
	public static String start = "";
	public static String end = "";
	public static String startTime = "";
	public static String endTime = "";
	public static String info = "";
	public static int isLoop = 0;
	public static String day = "";
	public static String alert;
}


@Service
public class KakaoService {
	
	public String authToken(String authorization_code) {
		String access_Token = "";
		String refresh_Token ="";
		
		String scheduleres = "";

		
		System.out.println("authToken: "+authorization_code);
		
		String strUrl = "https://kauth.kakao.com/oauth/token"; // 토큰 요청 보낼 주소
		
		
		KakaoToken kakaoToken = new KakaoToken(); // 요청받을 객체

		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // url Http 연결 생성

			// POST 요청
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);// outputStreamm으로 post 데이터를 넘김

			// 파라미터 세팅
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();

			// 0번 파라미터 grant_type 입니다 authorization_code로 고정
			sb.append("grant_type=authorization_code");

			// 1번 파라미터 client_id입니다. 
			sb.append("&client_id=d54083f94196531e75d7de474142e52e");

			// 2번 파라미터 redirect_uri입니다.
			sb.append("&redirect_uri=http://localhost:8182/calendar");

			// 3번 파라미터 code
			sb.append("&code=" + authorization_code);


			bw.write(sb.toString());
			bw.flush();// 실제 요청을 보내는 부분

			// 실제 요청을 보내는 부분, 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode"+responseCode);
		//	log.info("responsecode(200이면성공): {}", responseCode);
			
	
			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

		//	log.info("response body: {}", result);

			// Jackson으로 json 파싱할 것임
			ObjectMapper mapper = new ObjectMapper();
			// kakaoToken에 result를 KakaoToken.class 형식으로 변환하여 저장
			kakaoToken = mapper.readValue(result, KakaoToken.class);
			System.out.println("kakaoToken"+kakaoToken);

			// api호출용 access token
			access_Token = kakaoToken.getAccess_token();
			// access 토큰 만료되면 refresh token사용(유효기간 더 김)
			refresh_Token = kakaoToken.getRefresh_token();

		//	log.info(access_Token);
		//	log.info(refresh_Token);
			
//			scheduleres= scheduleadd(access_Token);
//			System.out.println("add schedule >" + scheduleres);

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//log.info("카카오토큰생성완료>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return access_Token;
	}
	 private static LocalTime roundToNearestFiveMinutes(LocalTime time) {
	        int minute = time.getMinute();
	        int roundedMinute = ((minute + 4) / 5) * 5;
	        return LocalTime.of(time.getHour(), roundedMinute);
	    }
	
	
	public String scheduleadd(String access_token) {
		System.out.println("scheduleadd " + access_token);
		String response1 = "";
		
	    String apiUrl = "https://kapi.kakao.com/v2/api/calendar/create/event";
		String calendar_id = "primary";
		StringBuilder result;
		
		String startAt = data.startTime+":00";
		String endAt = data.endTime+":00";
		
		
		String decodedStartAt = URLDecoder.decode(startAt, StandardCharsets.UTF_8);
		String decodedEndAt = URLDecoder.decode(endAt, StandardCharsets.UTF_8);
		
		String[] timeComponents = decodedStartAt.split(":");
	    int hour = Integer.parseInt(timeComponents[0]);
	    int minute = Integer.parseInt(timeComponents[1]);
	    int second = Integer.parseInt(timeComponents[2]);
	    
	    LocalTime time = LocalTime.of(hour, minute, second);
	    LocalTime time2 = LocalTime.of(hour, minute+5, second);

	    LocalTime adjustedTime = roundToNearestFiveMinutes(time);
	    LocalTime adjustedTime2 = roundToNearestFiveMinutes(time2);
		
		String adjustedStartAt = (adjustedTime+":00").toString();
		String adjustedStartAt2 = (adjustedTime2+":00").toString();
	    System.out.println("Adjusted startAt: " + adjustedStartAt);
	    System.out.println("Adjusted endAt: " + adjustedStartAt2);

	    String title = data.title;
	    String info = data.info;
	    
	    
	    
	    System.out.println(title);
	    
	    String decodedTitle = URLDecoder.decode(title, StandardCharsets.UTF_8);
	    String decodedInfo = URLDecoder.decode(info, StandardCharsets.UTF_8);
		
		try {
			  URL url = new URL(apiUrl);
	          HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	          connection.setRequestMethod("POST");
	          connection.setRequestProperty("Authorization", "Bearer " + access_token);
	          connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//			String event = "{\n";
//				   event += "title: "+data.title+",\n";
//				   event += "time: {\n";
//				   event += "start_at: "+data.start+"T"+data.startTime+",\n";
//				   event += "end_at: "+data.end+"T"+data.endTime+",\n";
//				   event += "time_zone: Asia/Seoul,\n";
//				   event += "all_day: false,\n";
//				   event += "lunar: false\n";
//				   event += "},\n";
//				   event += "rrlue:FREQ=DAILY;UNTIL=20231231T000000Z,\n";
//				   event += "description: "+data.info+",\n";
//				   event += "location: {\n";
//				   event += "name: 카카오,\n";
//				   event += "location_id: 18577297,\n";
//				   event += "address: 경기 성남시 분당구 판교역로 166,\n";
//				   event += "latitude: 37.39570088983171,\n";
//				   event += "longitude: 127.1104335101161\n";
//				   event += "},\n";
//				   event += "reminders: [15, 30],\n";
//				   event += "color: RED\n";
//				   event += "}";
				   
	          String requestBody = "calendar_id=primary&event="
	                    + "{\"title\":\"" + decodedTitle + "\", \"time\":{\"start_at\": \"" + data.start + "T" + adjustedStartAt + "Z\", \"end_at\": \"" + data.end + "T" + adjustedStartAt2 + "Z\", \"time_zone\": \"Asia/Seoul\", \"all_day\": false, \"lunar\": false }, \"description\": \"" + decodedInfo + "\"}";


			 	System.out.println(requestBody);
			 	connection.setDoOutput(true);
			 	connection.getOutputStream().write(requestBody.getBytes());
			 	
			 	int responseCode = connection.getResponseCode();
			 	System.out.println("Response Code:" +responseCode);
			 	
			 	
			
		
			 	BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			 	String line;
			 	StringBuilder response = new StringBuilder();
			 	
			 	while((line=reader.readLine()) != null) {
			 		response.append(line);
			 	}
			 	result = response;
			 	reader.close();
			 	connection.disconnect();
			 	System.out.println("response:"+ result.toString());
			 	response1 = result.toString(); 
			 	
			 	
	
    } catch (Exception e) {
        e.printStackTrace();
       
       response1 = e.toString();
		
			
	
    }
		return response1;

		}
}
