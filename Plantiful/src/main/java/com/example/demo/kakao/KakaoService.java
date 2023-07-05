package com.example.demo.kakao;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
			System.out.println(kakaoToken);

			// api호출용 access token
			access_Token = kakaoToken.getAccess_token();
			// access 토큰 만료되면 refresh token사용(유효기간 더 김)
			refresh_Token = kakaoToken.getRefresh_token();

		//	log.info(access_Token);
		//	log.info(refresh_Token);

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//log.info("카카오토큰생성완료>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return access_Token;
	}
	
	
	
	public String scheduleadd(String access_token) {
		System.out.println("scheduleadd " + access_token);
		String header = "Bear " + access_token;
		String calendar_id = "primary";
		String result = "";
		try {
			String apiURL = "https://kapi.kakao.com/v2/api/calendar/create/event";
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
				   
			 String payload = "{\n" +
		                "    \"title\": \""+data.title+"\",\n" +
		                "    \"time\": {\n" +
		                "        \"start_at\": \""+data.start+"T"+data.startTime+"\",\n" +
		                "        \"end_at\": \""+data.end+"T"+data.endTime+"\",\n" +
		                "        \"time_zone\": \"Asia/Seoul\",\n" +
		                "        \"all_day\": false,\n" +
		                "        \"lunar\": false\n" +
		                "    },\n" +
		                "    \"rrlue\":\"FREQ=DAILY;UNTIL=20221031T000000Z\",\n" +
		                "    \"description\": \""+data.info+"\",\n" +
		                "    \"location\": {\n" +
		                "        \"name\": \"cacao\",\n" +
		                "        \"location_id\": 18577297,\n" +
		                "        \"address\": \"166 Pangyoyeok-ro, Bundang-gu, Seongnam-si, Gyeonggi-do\",\n" +
		                "        \"latitude\": 37.39570088983171,\n" +
		                "        \"longitude\": 127.1104335101161\n" +
		                "    },\n" +
		                "    \"reminders\": [15, 30],\n" +
		                "    \"color\": \"RED\"\n" +
		                "}"; 
		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("POST");
	    con.setRequestProperty("Authorization", "Bearer " + access_token);
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");   
		
		String postParams = "calendar_id=primary&event="+payload;
		System.out.println(postParams);
		
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postParams);
		wr.flush();
		wr.close();
		
		int responseCode= con.getResponseCode();
		System.out.println("Response Code:"+responseCode);
		
		BufferedReader br;
		if(responseCode == 200) { // 정상호출
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else {
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		String inputLine;
		StringBuffer response = new StringBuffer();
		while((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		}
		br.close();
		System.out.println(response.toString());
		result = response.toString();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			result = e.toString();
		}
		return result;
			
	
}
}
