package com.example.demo.naver;




import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
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
	        String calendarId = "YOUR_CALENDAR_ID";
	        String title = "Meeting";
	        String description = "Discuss project updates";
	        String startDateTime = "2023-06-30T10:00:00";
	        String endDateTime = "2023-06-30T11:00:00";
	        String url = "https://openapi.naver.com/calendar/createSchedule.json";

	        OkHttpClient client = new OkHttpClient();

	        MediaType mediaType = MediaType.parse("application/json");
	        RequestBody body = RequestBody.create(mediaType, "{\n    \"calendarId\":\"" + calendarId + "\",\n    \"title\":\"" + title + "\",\n    \"description\":\"" + description + "\",\n    \"startDateTime\":\"" + startDateTime + "\",\n    \"endDateTime\":\"" + endDateTime + "\"\n}");
	        Request request = new Request.Builder()
	                .url(url)
	                .post(body)
	                .addHeader("Content-Type", "application/json")
	                .addHeader("Authorization", "Bearer " + access_token)
	                .build();

	        try {
	            Response response = client.newCall(request).execute();
	            if (response.isSuccessful()) {
	            	flag = true;
	                System.out.println("Schedule added successfully!");
	            } else {
	                System.out.println("Failed to add schedule. Error: " + response.body().string());
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        map.put("flag", flag);
	        return map;
	        
	}
} 