package com.example.demo.member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class CheckinfoService {

	// 인증코드로 token요청하고 사용자 phone 만 빼내기
	public HashMap getAccessToken(String impUid) {

		HashMap map = new HashMap<>();
		System.out.println("impUid");

		String impKey = "6828054376104647";
		String impSecret = "0kYhkRiK36tiyg4YtBaERfyhA7TcnJx496IEGOE44gQMXSeT7NjTgRnc5pYHEQVl4CXmOpGwSRbNiZLC";
		String strUrl = "https://api.iamport.kr/users/getToken"; // 토큰 요청 보낼 주소
		String access_token = " ";
		String phone = "";
		String name = "";

		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // url Http 연결 생성

			// POST 요청
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);// outputStreamm으로 post 데이터를 넘김

			conn.setRequestProperty("content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");

			// 파라미터 세팅
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

			JSONObject requestData = new JSONObject();
			requestData.put("imp_key", impKey);
			requestData.put("imp_secret", impSecret);

			bw.write(requestData.toString());
			bw.flush();
			bw.close();

			int resposeCode = conn.getResponseCode();

			System.out.println("응답코드 =============" + resposeCode);
			if (resposeCode == 200) {// 성공
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}

				br.close();

				// 토큰 값 빼기
				String response = sb.toString();
				JsonParser parser = new JsonParser();
				JsonObject responseJson = parser.parse(response).getAsJsonObject();
				access_token = responseJson.getAsJsonObject("response").get("access_token").getAsString();
				System.out.println("Access Token: " + access_token);

				// 본인인증한 사람 정보 뺴오기
				String getPaymentUrl = "https://api.iamport.kr/certifications/" + impUid;
				HttpURLConnection getConn = (HttpURLConnection) new URL(getPaymentUrl).openConnection();
				getConn.setRequestMethod("GET");
				getConn.setRequestProperty("Content-Type", "application/json");
				getConn.setRequestProperty("Authorization", "Bearer " + access_token);

				int getResponseCode = getConn.getResponseCode();
				System.out.println("GET 응답코드 =============" + getResponseCode);
				
				if (getResponseCode == 200) { // 성공
					BufferedReader getBr = new BufferedReader(new InputStreamReader(getConn.getInputStream()));
					StringBuilder getResponseSb = new StringBuilder();
					String getLine;
					while ((getLine = getBr.readLine()) != null) {
						getResponseSb.append(getLine).append("\n");
					}
					getBr.close();

					
					String getResponse = getResponseSb.toString();
					System.out.println("GET 응답 결과: " + getResponse);
					JsonParser parser1 = new JsonParser();
					JsonObject phoneJson1 = parser1.parse(getResponse).getAsJsonObject();
					
					// 전화번호 값 빼기
					phone = phoneJson1.getAsJsonObject("response").get("phone").getAsString();
					System.out.println("phone: " + phone);
					
					map.put("phone", phone);
					//이름 값 빼기 
					name =  phoneJson1.getAsJsonObject("response").get("name").getAsString();
					System.out.println("이름>>>>>" + name);
					map.put("name", name);
					
				} else {
					System.out.println("GET 에러 응답 메시지: " + getConn.getResponseMessage());
				}
			} else {
				System.out.println(conn.getResponseMessage());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return map;
	}

}
