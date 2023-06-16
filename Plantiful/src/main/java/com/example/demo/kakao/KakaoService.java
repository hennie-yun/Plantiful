package com.example.demo.kakao;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class KakaoService {
	String access_Token = "299bd0e4521985cacad8c0ac1326720c";
	String refresh_Token = "http://localhost:8181";
	String reqURL = "http://kauth.kakao.com/oauth/token";
	
	try {
		URL url = new URL(reqURL);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		// post 요청을 위해 기본값이 false인 setDoOutput을 true
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		
		// post 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		StringBuilder sb = new StringBuilder();
	
	} catch(Exception e) {
		System.out.println(e);
		
	}
}
}
