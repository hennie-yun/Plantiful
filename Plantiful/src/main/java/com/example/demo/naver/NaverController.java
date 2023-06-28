package com.example.demo.naver;




import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.jsonwebtoken.io.IOException;

@CrossOrigin("*")
@RestController
public class NaverController {
	
	@Autowired
	private NaverService service;
	
	private String CLIENT_ID = "IiiFJKBOyzL3qvfXasPq";
	private String CLIENT_SECRET = "PtvyRoMmt_";
	private String TOKEN_REQUEST_URL = "http://localhost:8081/calendar";
	
	// state 생성
	@RequestMapping("/api/naver/naverLogin")
	public Map naverLogin() {
		Map map = new HashMap();
		  try {
			String code="";
			String redirectURI = URLEncoder.encode(TOKEN_REQUEST_URL, "UTF-8");
			SecureRandom random = new SecureRandom();
			String state = new BigInteger(130, random).toString();
			String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
			 apiURL += "&client_id=" + CLIENT_ID;
			 apiURL += "&redirect_uri=" + redirectURI;
			 apiURL += "&state=" + state;

		      URL url = new URL(apiURL);
		      HttpURLConnection con = (HttpURLConnection)url.openConnection();
		      con.setRequestMethod("GET");
		      int responseCode = con.getResponseCode();
		      BufferedReader br;
		      //System.out.print("responseCode="+responseCode);
		      if(responseCode==200) { // 정상 호출
		        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		      } else {  // 에러 발생
		        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		      }
		      String inputLine;
		      
		      String res = "";
		      while ((inputLine = br.readLine()) != null) {
		        res+=inputLine;
		      }
		      JsonParser parser = new JsonParser();
	          JsonElement element = parser.parse(res);
		      
		      code = element.getAsJsonObject().get("code").getAsString();
		      state = element.getAsJsonObject().get("state").getAsString();
		     
		      map.put("code", code);
		      map.put("state", state);
		      
		      br.close();
		    } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (java.io.IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
	      return map;
	}
	
	
	
	@RequestMapping("/api/naver/callback")
	public Map naverCallback(String code, String state) throws JsonProcessingException{
		System.out.println("code "+code);
		System.out.println("state "+state);
	
		String redirectURI = "";
		String apiURL = "";
		
		Map map = new HashMap();
		   try {
				redirectURI = URLEncoder.encode("http://localhost:8181/api/naver/callback", "UTF-8");
				apiURL += "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
				apiURL += "client_id="+CLIENT_ID;
				apiURL += "&client_secret=" +CLIENT_SECRET;
				apiURL += "&redirect_uri="+redirectURI;
				apiURL += "&code="+code;
				apiURL += "&state="+state;
				  String access_token = "";
				    String refresh_token = "";
				    System.out.println("apiURL="+apiURL);
				 
				      URL url = new URL(apiURL);
				      HttpURLConnection con = (HttpURLConnection)url.openConnection();
				      con.setRequestMethod("GET");
				      int responseCode = con.getResponseCode();
				      BufferedReader br;
				      //System.out.print("responseCode="+responseCode);
				      if(responseCode==200) { // 정상 호출
				        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				      } else {  // 에러 발생
				        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				      }
				      String inputLine;
				      
				      String res = "";
				      while ((inputLine = br.readLine()) != null) {
				        res+=inputLine;
				      }
				      JsonParser parser = new JsonParser();
			          JsonElement element = parser.parse(res);
				      
				      access_token = element.getAsJsonObject().get("access_token").getAsString();
				      refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();
				     
				      map.put("access_token", access_token);
				      
				      br.close();
				    } catch (IOException e) {
			            // TODO Auto-generated catch block
			            e.printStackTrace();
			        } catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (java.io.IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		return map;
		   
	}
	
	@RequestMapping("/api/naver/calendar")
	public Map calendar(String token) {
		boolean flag = false;
		String header = "Bearer " + token;
		Map map = new HashMap();
		try {
			String apiURL = "https://openapi.naver.com/calendar/createSceduele.json";
			URL url = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", header);
			String calSum = URLEncoder.encode("[제목] 캘린더API로 추가한 일정", "UTF-8");
			String calDes = URLEncoder.encode("[상세] 집에가자", "UTF-8");
			String calLoc = URLEncoder.encode("[장소] kosta", "UTF-8");
			String uid = UUID.randomUUID().toString();
			String scheduleIcalString = "BEGIN:VCALENDAR\n" +
	                    "VERSION:2.0\n" +
	                    "PRODID:Naver Calendar\n" +
	                    "CALSCALE:GREGORIAN\n" +
	                    "BEGIN:VTIMEZONE\n" +
	                    "TZID:Asia/Seoul\n" +
	                    "BEGIN:STANDARD\n" +
	                    "DTSTART:20230628T000000\n" +
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
	                    "DTSTART;TZID=Asia/Seoul:20161116T170000\n" +  // 시작 일시
	                    "DTEND;TZID=Asia/Seoul:20161116T173000\n" +    // 종료 일시
	                    "SUMMARY:"+ calSum +" \n" +                    // 일정 제목
	                    "DESCRIPTION:"+ calDes +" \n" +                // 일정 상세 내용
	                    "LOCATION:"+ calLoc +" \n" +                   // 장소
	                    "RRULE:FREQ=YEARLY;BYDAY=FR;INTERVAL=1;UNTIL=20201231\n" +  // 일정 반복시 설정
	                    "ORGANIZER;CN=관리자:mailto:admin@sample.com\n" + // 일정 만든 사람
	                    "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;CN=admin:mailto:user1@sample.com\n" + // 참석자
	                    "CREATED:20161116T160000Z\n" +         // 일정 생성시각
	                    "LAST-MODIFIED:20161116T160000Z\n" +   // 일정 수정시각
	                    "DTSTAMP:20161116T160000Z\n" +         // 일정 타임스탬프
	                    "END:VEVENT\n" +
	                    "END:VCALENDAR";
			String postParam = "calendatId=defaultCalendarId&scheduleIcalString="+scheduleIcalString;
			System.out.println(postParam);
			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(postParam);
			wr.flush();
			wr.close();
			int responseCode = conn.getResponseCode();
			BufferedReader br;
			if(responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				flag = true;
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				
			}
			String inputLine;
			String response = "";
			while((inputLine = br.readLine()) != null) {
				response += inputLine;
			}
			  JsonParser parser = new JsonParser();
			  System.out.println(response);
		      
		      br.close();
		      map.put("flag", flag);
	    } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
} 