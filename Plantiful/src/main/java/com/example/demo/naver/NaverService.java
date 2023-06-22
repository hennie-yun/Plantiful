package com.example.demo.naver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;

public class NaverService {


        String token = "IiiFJKBOyzL3qvfXasPq";// 네아로 접근 토큰 값";
        String header = "Bearer " + token; // Bearer 다음에 공백 추가
        String clientId = "IiiFJKBOyzL3qvfXasPq";
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString();
        try {
            String redirectURL = URLEncoder.encode("http://localhost:8181/oauth","UTF-8");
            
            String apiURL = "https://openapi.naver.com/v1/nid/me";
            apiURL += "&client_id="+clientId;
            apiURL += "&redirect_uri="+redirectURL;
            apiURL += "&state="+state;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    }
}
