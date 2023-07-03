package com.example.demo.naver;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.groupparty.GroupPartyService;
import com.example.demo.member.Member;
import com.example.demo.schedule.ScheduleDto;
import com.example.demo.schedule.ScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin("*")
@RequestMapping("/api/naver")
@RestController
public class NaverController {
	
	@Autowired
	private NaverService naverService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	
	private String CLIENT_ID = "IiiFJKBOyzL3qvfXasPq";
	private String CLIENT_SECRET = "PtvyRoMmt_";
	private String state = "";
	private String access_token="";
	
	/*
	 * public String getstate() { SecureRandom random = new SecureRandom(); String
	 * state = new BigInteger(130, random).toString(); return state; }
	 * 
	 * 
	 * 
	 * @RequestMapping("/oauth") public String oauth(@RequestParam("code") String
	 * code, Model model) throws Exception{ String access_Token = ""; String
	 * refresh_Token = "";
	 * 
	 * //String access_token = getAccessToken(code);
	 * //System.out.println(access_token); return code;
	 * 
	 * }
	 */


//	@RequestMapping("/callback")
//	private String getAccessToken() {
//		// TODO Auto-generated method stub
//		
//	    String refresh_token = "";
//		String access_token = "";
//		try {
//		
//			    System.out.println("apiURL="+apiURL);
//			
//			      URL url = new URL(apiURL);
//			      HttpURLConnection con = (HttpURLConnection)url.openConnection();
//			      con.setRequestMethod("GET");
//			      int responseCode = con.getResponseCode();
//			      BufferedReader br;
//			      System.out.print("responseCode="+responseCode);
//			      if(responseCode==200) { // 정상 호출
//			        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//			      } else {  // 에러 발생
//			        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
//			      }
//			      String inputLine;
//			      String res ="";
//			      while ((inputLine = br.readLine()) != null) {
//			        res +=inputLine;
//			      }
//			      br.close();
//			      if(responseCode==200) {
//			        System.out.println(res.toString());
//			      }
//			        	JsonParser parser = new JsonParser();
//			            JsonElement element = parser.parse(res);
//
//			            access_token = element.getAsJsonObject().get("access_token").getAsString();
//			            refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();
//
//			            System.out.println("access_token : " + access_token);
//			            System.out.println("refresh_token : " + refresh_token);
//		        } catch (MalformedURLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					
//				} catch (java.io.IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		return access_token;
//	}
//	@PostMapping("/token")
//    public ResponseEntity<String> requestTokens() {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("client_id", CLIENT_ID);
//        body.add("client_secret", CLIENT_SECRET);
//        body.add("grant_type", "authorization_code");
//        body.add("redirect_uri", "http://localhost:8181/api/naver/callback");
//        body.add("code", "YourAuthorizationCode");
//
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                "https://nid.naver.com/oauth2.0/token",
//                HttpMethod.POST,
//                requestEntity,
//                String.class
//        );
//
//        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//            String tokenResponse = responseEntity.getBody();
//            System.out.println(ResponseEntity.ok(tokenResponse));
//            // Handle the token response here as needed
//            return ResponseEntity.ok(tokenResponse);
//        } else {
//            // Handle error response
//            return ResponseEntity.status(responseEntity.getStatusCode()).build();
//        }
//    }
//	
//
//    @GetMapping("/callback")
//    public String handleCallback(
//            @RequestParam("code") String authorizationCode,
//            @RequestParam("state") String state) {
//
//        // Perform any necessary operations with the received authorization code and state
//        // For example, you can use the authorization code to request the access token
//        
//        // Replace the following lines with your desired logic
//        String message = "Received authorization code: " + authorizationCode + "\nState: " + state;
//   System.out.println("callback"+message+authorizationCode);
//        return authorizationCode;
//    }
//	
//	public void getUserInfo(String access_token) {
//	        String header = "Bearer " + access_token; // Bearer 다음에 공백 추가
//
//
//	        String apiURL = "https://openapi.naver.com/v1/nid/me";
//
//	        Map<String, String> requestHeaders = new HashMap<>();
//	        requestHeaders.put("Authorization", header);
//	        String responseBody = get(apiURL,requestHeaders);
//
//
//	        System.out.println(responseBody);
//	}
//	
//	   private static String get(String apiUrl, Map<String, String> requestHeaders){
//	        HttpURLConnection con = connect(apiUrl);
//	        try {
//	            con.setRequestMethod("GET");
//	            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
//	                con.setRequestProperty(header.getKey(), header.getValue());
//	            }
//
//
//	            int responseCode = con.getResponseCode();
//	            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
//	                return readBody(con.getInputStream());
//	            } else { // 에러 발생
//	                return readBody(con.getErrorStream());
//	            }
//	        } catch (IOException e) {
//	            throw new RuntimeException("API 요청과 응답 실패", e);
//	        } finally {
//	            con.disconnect();
//	        }
//	    }
//
//
//	    private static HttpURLConnection connect(String apiUrl){
//	        try {
//	            URL url = new URL(apiUrl);
//	            return (HttpURLConnection)url.openConnection();
//	        } catch (MalformedURLException e) {
//	            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
//	        } catch (IOException e) {
//	            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
//	        }
//	    }
//
//
//	    private static String readBody(InputStream body){
//	        InputStreamReader streamReader = new InputStreamReader(body);
//
//
//	        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
//	            StringBuilder responseBody = new StringBuilder();
//
//
//	            String line;
//	            while ((line = lineReader.readLine()) != null) {
//	                responseBody.append(line);
//	            }
//
//
//	            return responseBody.toString();
//	        } catch (IOException e) {
//	            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
//	        }
//	    }
	
	@GetMapping("/oauth")
	public ResponseEntity<?> naverConnect() throws UnsupportedEncodingException {
	    String url = naverService.createNaverURL();

	    return new ResponseEntity<>(url, HttpStatus.OK); // 프론트 브라우저로 보내는 주소
	}

	@RequestMapping("/callback")
	public String naverLogin(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletResponse response) throws JsonProcessingException {
		access_token =  naverService.loginNaver(code, state, response);
	    System.out.println("callback");
	    System.out.println("access_token"+access_token);
	    // 받아온 정보로 서비스 로직에 적용하기
       // Member naverMember = memberService.createNaverMember(naverProfile, naverToken.getAccess_token());

        // 시큐리티 영역
        // Authentication 을 Security Context Holder 에 저장
        //Authentication authentication = new UsernamePasswordAuthenticationToken(naverMember.getEmail(), naverMember.getPassword());
        //SecurityContextHolder.getContext().setAuthentication(authentication);

        // 자체 JWT 생성 및 HttpServletResponse 의 Header 에 저장 (클라이언트 응답용)
        //String accessToken = jwtTokenizer.delegateAccessToken(naverMember);
        //String refreshToken = jwtTokenizer.delegateRefreshToken(naverMember);
        //response.setHeader("Authorization", "Bearer " + accessToken);
        //response.setHeader("RefreshToken", refreshToken);

        return "Success Login: User"; // 클라이언트 바디로 해당 메세지가 전달된다.

	    /* Header가 아닌 Redis 서버에 잘 저장이 되었는지 확인하기 */
	   // return response.getHeader("Authorization") == null ? "Fail Login: User" :  "Success Login: User";
	}
	
	@RequestMapping("/calendar")
	public String calendar(ScheduleDto dto) {
		
		String email = dto.getEmail().getEmail();
	    System.out.println(dto.toString());

		 try {
			content.calSum =  URLEncoder.encode("[plantiful] " + dto.getTitle(), "UTF-8");
			content.calDes =  URLEncoder.encode("[plantiful] " + dto.getInfo(), "UTF-8");
			content.calStart = URLEncoder.encode(dto.getStart().replace("-", ""), "UTF-8");
			content.calEnd = URLEncoder.encode(dto.getEnd().replace("-", ""), "UTF-8");
			content.calStartTime = URLEncoder.encode(dto.getStartTime().replace(":", "")+"00", "UTF-8");
			content.calEndTime = URLEncoder.encode(dto.getEndTime().replace(":", "")+"00", "UTF-8");
			content.calEmail = URLEncoder.encode(email, "UTF-8");
			content.calGroup = URLEncoder.encode(scheduleService.getMemberEmail(dto), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		ArrayList<Member> list = groupservice.getGroupMemberByScheduleNum(dto.getSchedule_num());
//		String member="";
//		for(Member m : list) {
//			member += m;
//		}
//		content.calGroup = URLEncoder.encode(member, "UTF-8");
		 
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");	 
	System.out.println(content.calSum);
	System.out.println(content.calDes);
	System.out.println(content.calStart);
	System.out.println(content.calEnd);
	System.out.println(content.calStartTime);
	System.out.println(content.calEndTime);
	System.out.println(content.calEmail);
	System.out.println(content.calGroup);
	    return naverService.scheduleadd(access_token);
//		return dto.toString();
		
	}
	
	
	
	
	
	

	
} 