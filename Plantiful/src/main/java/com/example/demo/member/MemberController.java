package com.example.demo.member;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.auth.JwtTokenProvider;

import io.jsonwebtoken.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/members")
public class MemberController {

	@Autowired
	private MemberService service;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private JwtTokenProvider tokenprovider;
	// 회원가입/세션아이디발급/검색/내정보수정/삭제

	@Value("${spring.servlet.multipart.location}")
	private String path; // C:/plantiful/
	
	// 가입
	@PostMapping("")
	public Map join(MemberDto dto) {
		//MemberDto d = service.save(dto);
		Map map = new HashMap();
		try {
	        // 이미지 파일이 제공되었는지 확인
	        MultipartFile imageFile = dto.getF();
	        if (imageFile != null && !imageFile.isEmpty()) {
	            String originalFilename = imageFile.getOriginalFilename();
	            // 이미지 파일을 원하는 위치에 저장
	            String filePath = path + originalFilename;
	            File file = new File(filePath);
	            imageFile.transferTo(file);
	            dto.setImg(originalFilename);
	        }

	        MemberDto Dto = service.save(dto);
	        map.put("success", true);
	        map.put("message", "회원 가입이 성공");
	        map.put("dto", Dto);
	    } catch (Exception e) {
	        map.put("success", false);
	        map.put("message", "회원 가입에 실패");
	        e.printStackTrace();
	    }
		return map;
	}

	//로그인
	@PostMapping("/login") 
	public Map login(String email, String pwd) {
		Map map = new HashMap();
		boolean flag = false;
		MemberDto dto = service.getMember(email);
		if (dto != null && pwd.equals(dto.getPwd())) {
			String token = tokenprovider.generateJwtToken(dto);
			flag = true;
			map.put("token", token);
		}
		map.put("flag", flag);
		return map;
	}

	
	@PostMapping("/token")
	public Map getByToken(String token) {
		boolean flag = true;
		Map map = new HashMap();
		try {
			// 받은 토큰에서 로그인한사람의 아이디, 타입 정보 추출
			String email = tokenprovider.getUsernameFromToken(token);
			map.put("email", email);
		} catch (Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		return map;
	}


	//토큰값으로 정보 불러오기
	@GetMapping("")
	public Map getInfo(@RequestHeader("token") String token) {
		boolean flag = true;
		Map map = new HashMap();
		try {
			String email = tokenprovider.getUsernameFromToken(token);
			map.put("email", email);
		} catch (Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		return map;
	}


	@GetMapping("/{email}")
	public Map get(@PathVariable("email") String email, @RequestHeader(name = "token", required = false) String token) {
		Map map = new HashMap();
		if (token != null) {// 로그인 후
			try {
				String id = tokenprovider.getUsernameFromToken(token);
				if (!email.equals(id)) {
					map.put("dto", null);
					return map;
				}
			} catch (Exception e) {
				// flag = false;
				map.put("dto", null);
				return map;
			}
		}
		MemberDto d = service.getMember(email);
		map.put("dto", d);
		return map;
	}

	@PutMapping("")
	public Map edit(MemberDto dto, @RequestHeader(name = "token", required = false) String token) {
		boolean flag = true;
		Map map = new HashMap();
		if (token != null) {// 로그인 후
			try {
				String id = tokenprovider.getUsernameFromToken(token);
				if (!id.equals(dto.getEmail())) {
					flag = false;
				}
			} catch (Exception e) {
				// flag = false;
				flag = false;
			}
		}
		if (flag) {
			MemberDto old = service.getMember(dto.getEmail());
			old.setPwd(dto.getPwd());
			old.setNickname(dto.getNickname());
			old.setPhone(dto.getPhone());
			MemberDto d = service.save(old);
			map.put("dto", d);
		}
		map.put("flag", flag);
		return map;
	}

	@GetMapping("/read_img")
	public Map read_img(String fname) {
	    Map resultMap = new HashMap<>();

	    try {
	        File f = new File(path + fname); // 이미지 파일 경로
	        if (f.exists()) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.add("Content-Type", Files.probeContentType(f.toPath()));

	            byte[] imageBytes = Files.readAllBytes(f.toPath());
	            resultMap.put("success", true);
	            resultMap.put("message", "이미지 파일을 성공적으로 읽어왔습니다.");
	            resultMap.put("data", imageBytes);
	            resultMap.put("headers", headers);
	        } else {
	            resultMap.put("success", false);
	            resultMap.put("message", "이미지 파일을 찾을 수 없습니다.");
	        }
	    } catch (IOException e) {
	        resultMap.put("success", false);
	        resultMap.put("message", "이미지 파일을 읽어오는 중에 오류가 발생했습니다.");
	        e.printStackTrace();
	    } catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return resultMap;
	}
	
	@DeleteMapping("/{email}")
	public Map del(@PathVariable("email") String email, @RequestHeader(name = "token", required = false) String token) {
		boolean flag = true;
		Map map = new HashMap();
		if (token != null) {
			try {
				String id = tokenprovider.getUsernameFromToken(token);
				if (!email.equals(id)) {
					flag = false;
				}
			} catch (Exception e) {
				// flag = false;
				flag = false;
			}
		}
		if (flag) {
			service.delMember(email);
		}
		map.put("flag", flag);
		return map;
	}
	
	//이메일 인증 
	@ResponseBody
	   @PostMapping("/email")
	   public Map email(String email) {
	      Map map = new HashMap<>();
	      MemberDto dto = service.getMember(email);
	      if(dto != null) {
	         map.put("exist", "이미 존재하는 이메일입니다.");
	      } else {
	         Random random=new Random();  //난수 생성을 위한 랜덤 클래스
	         String key="";  //인증번호 
	         
	         SimpleMailMessage message = new SimpleMailMessage(); // 이메일 제목, 내용 작업 메서드
	         message.setTo(email); //스크립트에서 보낸 메일을 받을 사용자 이메일 주소 
	         //입력 키를 위한 코드
	         //난수 생성 
	         for(int i=0; i<3;i++) {
	            int index=random.nextInt(26)+65; 
	            key+=(char)index;
	         }
	         for(int i=0; i<6; i++) {
	            int numIndex=random.nextInt(10); 
	            key+=numIndex;
	         }
	         String mail = "\n Plantiful 회원가입 이메일 인증.";
	         message.setSubject("회원가입을 위한 이메일 인증번호 전송 메일입니다."); // 이메일 제목
	         message.setText("인증번호는 " + key + " 입니다." + mail); // 이메일 내용
	         try {
	            javaMailSender.send(message); // 이메일 전송
	         } catch (Exception e) {
	            e.printStackTrace();
	         }
	         map.put("key", key);
	      }
	        return map;
	   }

}
