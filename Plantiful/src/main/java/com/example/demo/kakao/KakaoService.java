package com.example.demo.kakao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.example.demo.member.Member;
import com.example.demo.schedulegroup.ScheduleGroup;

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
	
	
	
	public String scheduleadd(String access_token) {
		System.out.println("scheduleadd " + access_token);
		String header = "Bear " + access_token;
		String calendar_id = "primary";
		String result = "";
		try {
			String apiURL = "https://kapi.kakao.com/v2/api/calendar/create/event";
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Authorization", header);
			
			String event = "'event={\n";
				   event += "title: "+data.title+",\n";
				   event += "time: {\n";
				   event += "start_at: "+data.start+"T"+data.startTime+",\n";
				   event += "end_at: "+data.end+"T"+data.endTime+",\n";
				   event += "time_zone: Asia/Seoul,\n";
				   event += "all_day: false,\n";
				   event += "lunar: false\n";
				   event += "},\n";
				   event += "rrlue:FREQ=DAILY;UNTIL=20231231T000000Z,\n";
				   event += "description: "+data.info+",\n";
				   event += "location: {\n";
				   event += "name: 카카오,\n";
				   event += "location_id: 18577297,\n";
				   event += "address: 경기 성남시 분당구 판교역로 166,\n";
				   event += "latitude: 37.39570088983171,\n";
				   event += "longitude: 127.1104335101161\n";
				   event += "},\n";
				   event += "reminders: [15, 30],\n";
				   event += "color: RED\n";
				   event += "}'";
		String postParams = "calendar_id=primary"+event;
		System.out.println(postParams);
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postParams);
		wr.flush();
		wr.close();
		int responseCode= con.getResponseCode();
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
