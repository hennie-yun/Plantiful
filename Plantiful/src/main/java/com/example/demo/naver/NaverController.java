package com.example.demo.naver;




import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@CrossOrigin("*")
@RestController
public class NaverController {
	
	@Autowired
	private NaverService service;
	
	private String CLIENT_ID = "IiiFJKBOyzL3qvfXasPq";
	private String CLIENT_SECRET = "PtvyRoMmt_";
	private String TOKEN_REQUEST_URL = "http://localhost:8181/api/naver/callback";
	private String state = "";
	
	@RequestMapping("/api/naver/state")
	public Map getstate() {
		Map map = new HashMap();
		SecureRandom random = new SecureRandom();
		state = new BigInteger(130, random).toString();
		map.put("state", state);
		return map;
	}
	
	// state 생성
	@RequestMapping("/api/naver/naverLogin")
	public String naverLogin(HttpServletRequest request) throws Exception {
			
			getstate();
			String code="";
			String redirectURI;
			String apiURL = null;
			try {
				redirectURI = URLEncoder.encode(TOKEN_REQUEST_URL, "UTF-8");
				apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
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
			return state;
	}

	
	
	@RequestMapping("/api/naver/callback")
	public String getAccessToken(String code, String state) throws JsonProcessingException{
		System.out.println("token 가져오기");
		System.out.println("code "+code);
		System.out.println("state "+state);
	
		String redirectURI = "";
		String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		
		Map map = new HashMap();
		String access_token = "";
		   try {
				redirectURI = URLEncoder.encode("http://localhost:8181/api/naver/calendar", "UTF-8");
				apiURL += "client_id="+CLIENT_ID;
				apiURL += "&client_secret=" +CLIENT_SECRET;
				apiURL += "&redirect_uri="+redirectURI;
				apiURL += "&code="+code;
				apiURL += "&state="+state;
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
				     
				     // System.out.println(access_token);
				      
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
		return access_token;
		   
	}
	
	@RequestMapping("/api/naver/calendar")
	public Map calendar(@RequestHeader("access_token") String access_token) throws java.io.IOException {	
			boolean flag = false;
			
			Map map = new HashMap();
			  String token = "access_token";
		        String header = "Bearer " + access_token; // Bearer 다음에 공백 추가
		        try {
		            String apiURL = "https://openapi.naver.com/calendar/createSchedule.json";
		            URL url = new URL(apiURL);
		            HttpURLConnection con = (HttpURLConnection)url.openConnection();
		            con.setRequestMethod("POST");
		            con.setRequestProperty("Authorization", header);
		            String calSum =  URLEncoder.encode("[제목] 캘린더API로 추가한 일정", "UTF-8");
		            String calDes =  URLEncoder.encode("[상세] 회의 합니다", "UTF-8");
		            String calLoc =  URLEncoder.encode("[장소] 그린팩토리", "UTF-8");
		            String uid = UUID.randomUUID().toString();
		            String scheduleIcalString = "BEGIN:VCALENDAR\n" +
		                    "VERSION:2.0\n" +
		                    "PRODID:Naver Calendar\n" +
		                    "CALSCALE:GREGORIAN\n" +
		                    "BEGIN:VTIMEZONE\n" +
		                    "TZID:Asia/Seoul\n" +
		                    "BEGIN:STANDARD\n" +
		                    "DTSTART:20230629T000000\n" +
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
		                    "DTSTART;TZID=Asia/Seoul:20230629T170000\n" +  // 시작 일시
		                    "DTEND;TZID=Asia/Seoul:20230629T173000\n" +    // 종료 일시
		                    "SUMMARY:"+ calSum +" \n" +                    // 일정 제목
		                    "DESCRIPTION:"+ calDes +" \n" +                // 일정 상세 내용
		                    "LOCATION:"+ calLoc +" \n" +                   // 장소
		                    "RRULE:FREQ=YEARLY;BYDAY=FR;INTERVAL=1;UNTIL=20201231\n" +  // 일정 반복시 설정
		                    "ORGANIZER;CN=관리자:mailto:admin@sample.com\n" + // 일정 만든 사람
		                    "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;CN=admin:mailto:user1@sample.com\n" + // 참석자
		                    "CREATED:20230629T160000Z\n" +         // 일정 생성시각
		                    "LAST-MODIFIED:20230629T160000Z\n" +   // 일정 수정시각
		                    "DTSTAMP:20230629T160000Z\n" +         // 일정 타임스탬프
		                    "END:VEVENT\n" +
		                    "END:VCALENDAR";
		            String postParams = "calendarId=defaultCalendarId&scheduleIcalString=" + scheduleIcalString;
		            System.out.println(postParams);
		            con.setDoOutput(true);
		            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		            wr.writeBytes(postParams);
		            wr.flush();
		            wr.close();
		            int responseCode = con.getResponseCode();
		            BufferedReader br;
		            if(responseCode==200) { // 정상 호출
		                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		                flag= true;
		            } else {  // 에러 발생
		                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		            }
		            String inputLine;
		            StringBuffer response = new StringBuffer();
		            while ((inputLine = br.readLine()) != null) {
		                response.append(inputLine);
		            }
		            br.close();
		            map.put("flag", flag);
		            System.out.println(response.toString());
		        } catch (Exception e) {
		            System.out.println(e);
		        }
		        return map;
	}
	
	

} 