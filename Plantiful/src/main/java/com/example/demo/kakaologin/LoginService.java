package com.example.demo.kakaologin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginService {
	
	@Autowired
	private MemberService memberservice;

	// 인증코드로 token요청하기
	public String requestToken(String code) {
		String access_Token = "";
		String refresh_Token ="";

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
			sb.append("&redirect_uri=http://localhost:8182/kakaojoin");

			// 3번 파라미터 code
			sb.append("&code=" + code);

			sb.append("&client_secret=ffySwI0e3mED14F05NWZIOH2r0xy9YWH");

			bw.write(sb.toString());
			bw.flush();// 실제 요청을 보내는 부분

			// 실제 요청을 보내는 부분, 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			log.info("responsecode(200이면성공): {}", responseCode);
			
	
			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

			log.info("response body: {}", result);

			// Jackson으로 json 파싱할 것임
			ObjectMapper mapper = new ObjectMapper();
			// kakaoToken에 result를 KakaoToken.class 형식으로 변환하여 저장
			kakaoToken = mapper.readValue(result, KakaoToken.class);
			System.out.println(kakaoToken);

			// api호출용 access token
			access_Token = kakaoToken.getAccess_token();
			// access 토큰 만료되면 refresh token사용(유효기간 더 김)
			refresh_Token = kakaoToken.getRefresh_token();

			log.info(access_Token);
			log.info(refresh_Token);

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("카카오토큰생성완료>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return access_Token;

	}

	// 유저 정보 얻기
	public HashMap<String, String> requestUser(String accessToken) {
		
		String email = "";
		log.info("유저정보 요청 시작");
		String strUrl = "https://kapi.kakao.com/v2/user/me"; // request를 보낼 주소
		HashMap userInfo = new HashMap<>();
	
		userInfo.put("accessToken", accessToken);


		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // url Http 연결 생성

			// POST 요청
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);// outputStreamm으로 post 데이터를 넘김

			// 전송할 header 작성, 인자로 받은 access_token전송
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			// 실제 요청을 보내는 부분, 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			log.info("requestUser의 responsecode(200이면성공): {}", responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			br.close();

			log.info("response body: {}", result);

			// Jackson으로 json 파싱할 것임
			ObjectMapper mapper = new ObjectMapper();

			// 결과 json을 HashMap 형태로 변환하여 resultMap에 담음

			HashMap<String, Object> resultMap = mapper.readValue(result, HashMap.class);
			String id =String.valueOf((Long) resultMap.get("id"));
			
			System.out.println(id);
			
			userInfo.put("id", id);

			// json 파싱하여 id 가져오기

			// 결과json 안에 properties key는 json Object를 value로 가짐
			HashMap<String, Object> properties = (HashMap<String, Object>) resultMap.get("properties");
			String nickname = (String) properties.get("nickname");
			
			userInfo.put("nickname", nickname);

			// 결과json 안에 kakao_account key는 json Object를 value로 가짐
			HashMap<String, Object> kakao_account = (HashMap<String, Object>) resultMap.get("kakao_account");

			// 이메일은 동의 해야 알 수 있는데 그건 진짜 계정만 되서 일단 무조건 동의한다는 가정 아래 진행
			email = (String) kakao_account.get("email");
			MemberDto dto = memberservice.getMember(email);
			//카카오톡으로 로그인 하려고하는데 네이버로 이미 가입 된 이메일이 있다면 
			//혹은 이미 카카오로 회원가입을 진행 했다면? 
			if(dto!= null && dto.getId() == '2') { //멤버에 있는지 찾아서 있다면  
				email = null; //얻은 정보는 null을 만들고 
				userInfo.put("message", "동일 아이디로 회원가입 된 네이버 계정이 있습니다.");
				
			} else { //없다면 정보에 담아 
				userInfo.put("email", email);
			}

			// 만약 선책하는거라면 이렇게 해주면 될듯?
//	        String email=null;
//	        if(kakao_account.containsKey("email")){//동의하면 email이 kakao_account에 key로 존재함
//	            email=(String)kakao_account.get("email");
//	        }

			log.info("resultMap= {}", resultMap);
			log.info("properties= {}", properties);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userInfo;
	}



}
