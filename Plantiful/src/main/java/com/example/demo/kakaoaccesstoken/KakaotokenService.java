package com.example.demo.kakaoaccesstoken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class KakaotokenService {

	@Autowired
	private KakaotokenDao dao;

	// 초기 가입 추가 및 정보가 있다면 token만 수정
	public Kakaotoken edit(Kakaotoken ktoken) {
		Kakaotoken entity = dao
				.save(new Kakaotoken(ktoken.getTokennum(), ktoken.getToken(), ktoken.getEmail()));
		return entity;
	}

//이메일로 찾기
	@Transactional
	public Kakaotoken findByEmail(String email) {
		Kakaotoken ktoken = dao.findByEmail(email);
		if (ktoken == null) {
			return null;
		}
		return ktoken;
	}

	// 우리 DB에서도 삭제
	public void delKtoken(String email) {
		Kakaotoken ktoken = dao.findByEmail(email);
		dao.deleteById(ktoken.getTokennum());
	}

	// 카카오로 요청 보내서 탈퇴하게 하기
	public void outKakao(String token) {
		String accessToken = token;

		System.out.println(accessToken);
		// 요청 URL
		String url = "https://kapi.kakao.com/v1/user/unlink";

		try {
		// URL 객체 생성
		URL obj = new URL(url);

		// HttpURLConnection 생성 및 설정
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		conn.setRequestMethod("POST");

		// 요청 헤더에 인증 정보 추가
		conn.setRequestProperty("Authorization", "Bearer " + accessToken);

		// 요청 전송
		int responseCode = conn.getResponseCode();

		// 응답 읽기
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		// 응답 결과 확인
				System.out.println("Response Code: " + responseCode);
				System.out.println("Response Body: " + response.toString());

		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
