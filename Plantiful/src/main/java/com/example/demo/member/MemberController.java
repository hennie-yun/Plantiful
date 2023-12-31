package com.example.demo.member;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.auth.JwtTokenProvider;
import com.example.demo.subscribeparty.SubscribePartyDto;
import com.example.demo.subscribeparty.SubscribePartyService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/members")
public class MemberController {

	@Autowired
	private MemberService service;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private CheckinfoService checkinfoservice;

	@Autowired
	private JwtTokenProvider tokenprovider;
	// 회원가입/세션아이디발급/검색/내정보수정/삭제
	@Autowired
	private SubscribePartyService SubscribeParty;

	@Value("${spring.servlet.multipart.location}")
	private String path; // C:/plantiful/

	// 가입
	@PostMapping("")
	public Map join(MemberDto dto, @RequestParam(required = false) MultipartFile f) {
		boolean flag = true;
		HashMap map = new HashMap(); 
		
		String checkemail = dto.getEmail();
		System.out.println(checkemail);
		MemberDto serviceemail = service.getMember(checkemail);
		System.out.println(serviceemail);

		  if (serviceemail == null || serviceemail.getEmail() == null || serviceemail.getEmail().isEmpty()) {		
			  try {
			String email = service.save(dto);
			File dir = new File(path + "/" + email);
			dir.mkdir(); // 파일 디렉토리까지 생성

			if (f != null && !f.isEmpty()) {
				String fname = f.getOriginalFilename();
				String newpath = path + email + "/" + fname;
				File newfile = new File(newpath);

				try {
					f.transferTo(newfile); // 파일 업로드
					dto.setImg(newpath);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}

			service.save(dto);
			map.put("dtook", dto);
		} catch (Exception e) {
			flag = false;
		} 
		} else {
			map.put("msg", "중복된 아이디 입니다");
		}
		return map; 
	}

	// 이미지 제외 수정
	@PostMapping("/editinfo/{email}")
	public Map<String, Object> editinfo(@PathVariable("email") String email, @RequestBody MemberDto dto) {
		Map response = new HashMap<>();
		MemberDto oldDto = service.getMember(email);
		if (oldDto != null) {
			if (dto.getImg() == null) {
				dto.setImg(oldDto.getImg());
			}
			service.edit(dto);
			response.put("message", "정보 수정 완료.");
		}
		return response;
	}

	// 이미지 수정
	@PostMapping("/{email}/updateImg")
	public Map<String, Object> updateProfile(@PathVariable("email") String email,
			@RequestParam("file") MultipartFile file) {

		Map<String, Object> response = new HashMap<>();
		boolean flag = true;
		MemberDto dto = service.getMember(email);
		if (dto == null) {
			flag = false;
			response.put("flag", flag);

		}
		String newFilePath = path + email + "/";
		File dir = new File(newFilePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String fileName = file.getOriginalFilename();
		String newFileName = fileName;
		String newFileFullPath = newFilePath + newFileName;
		File newFile = new File(newFileFullPath);

		try {
			file.transferTo(newFile);
			String deletedFilePath = dto.getImg();
			dto.setImg(newFileFullPath);

			if (deletedFilePath != null) {
				File delFile = new File(deletedFilePath);
				delFile.delete();
			}
			service.edit(dto);
			response.put("message", "이미지 변경 완료.");
		} catch (IllegalStateException e) {
			e.printStackTrace();
			response.put("error", "이미지 업로드 실패. 상태 예외 발생.");
		} catch (IOException e) {
			e.printStackTrace();
			response.put("error", "이미지 업로드 실패. IO 예외 발생.");
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	// 이미지 폴더에서 삭제
	@PostMapping("/delprofile")
	public void deleteProfile(@RequestParam("email") String email) {
		MemberDto dto = service.getMember(email);
		String deletedFilePath = dto.getImg();
		dto.setImg(null);
		if (deletedFilePath != null) {
			File delFile = new File(deletedFilePath);
			delFile.delete();
		}
		service.save(dto);
	}

	// 비밀번호만 변경
	@PutMapping("/newpwd/{email}/{pwd}")
	public Map updatePassword(@PathVariable("email") String email, @PathVariable("pwd") String pwd) {
		boolean flag = true;
		Map response = new HashMap<>();
		MemberDto dto = service.getMember(email);
		if (dto == null) {
			flag = false;
		} else {
			MemberDto old = service.getMember(dto.getEmail());
			old.setPwd(pwd);
			old.setEmail(dto.getEmail());
			MemberDto updatedDto = service.edit(old);
			response.put("dto", updatedDto);
		}

		response.put("flag", flag);
		return response;
	}

	public Map newPassword(String email, String pwd) {
		boolean flag = true;
		Map response = new HashMap<>();
		MemberDto dto = service.getMember(email);
		if (dto == null) {
			flag = false;
		} else {
			MemberDto old = service.getMember(dto.getEmail());
			old.setPwd(pwd);
			old.setEmail(dto.getEmail());
			MemberDto updatedDto = service.edit(old);
			response.put("dto", updatedDto);
		}

		response.put("flag", flag);
		return response;
	}

	// 이미지 빼기
	@GetMapping("plantiful/{email}") // email/이미지파일명
	public ResponseEntity<byte[]> read_img(@PathVariable("email") String email) {
		String fname = "";
		MemberDto dto = service.getMember(email); // 이메일로 검색
		String imageUrl = dto != null ? dto.getImg() : null; // 이미지 URL 가져오기
		fname = dto.getImg();
		System.out.println(fname);
		File f = new File(fname);
		if (imageUrl != null && !imageUrl.isEmpty()) {
			// 이미지 파일이 존재하는 경우 해당 URL로 응답 생성
			HttpHeaders header = new HttpHeaders();
			ResponseEntity<byte[]> result = null;

			try {
				header.add("Content-Type", Files.probeContentType(f.toPath()));
				// 응답 객체 생성
				result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(f), header, HttpStatus.OK);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (java.io.IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
		return null;

	}

	// 로그인
	@PostMapping("/login")
	public Map login(String email, String pwd, HttpSession session) {
		Map map = new HashMap();
		boolean flag = false;
		MemberDto dto = service.getMember(email);
		if (dto != null && pwd.equals(dto.getPwd())) {
			String token = tokenprovider.generateJwtToken(dto);
			String loginId = dto.getEmail();
			flag = true;
			map.put("token", token);
			System.out.println("로그인의 토큰 " + token);
			map.put("loginId", loginId);
			session.setAttribute("loginId", loginId);
			System.out.println("loginId 아이디 : " + loginId);

		}
		map.put("flag", flag);
		return map;
	}

	// 토큰값
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

	// 토큰값으로 정보 불러오기
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

	// 정보 한개 빼오기
	@GetMapping("/getmember/{email}")
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
		//로그인 전 
		MemberDto d = service.getMember(email);
		long id = d.getId();
		map.put("dto", d);
		map.put("id", id);
		return map;
	}

	@DeleteMapping("/{email}")
	public Map<String, Object> del(@PathVariable("email") String email, @RequestHeader(name = "token", required = false) String token) {
	    boolean flag = true;
	    ArrayList<SubscribePartyDto> dto = SubscribeParty.getByEmail(email);
	    MemberDto memdto = service.getMember(email);
	    long id = memdto.getId();
	    System.out.println("디티오!!!!!!!!!!!!!!" + dto);
	    System.out.println("디티오!!!!!!!!!!!!!!" + memdto);

	    Map<String, Object> map = new HashMap<>();
	    
	    // 토큰 값이 존재하고, 해당 이메일과 토큰으로 얻은 아이디가 일치하지 않는 경우 flag를 false로 설정
	    if (token != null) {
	        try {
	            String getid = tokenprovider.getUsernameFromToken(token);
	            if (!email.equals(getid)) {
	                flag = false;
	            }
	        } catch (Exception e) {
	            flag = false;
	        }
	    }
	    
	    //회원가입 된게 네이버나 카카오톡이면 
	    if (id ==1 || id== 2) {
	    	map.put("id", id);
	    }
	    
	    // dto가 null이거나 비어있거나 start_check가 2인 경우 탈퇴 진행
	    if (dto != null && !dto.isEmpty() && dto.get(0).getStart_check() != 2) {
	        flag = false;
	        map.put("message", "가입한 파티가 있어 탈퇴 불가능합니다");
	    } else {
	        service.delMember(email);
	    }
	    
	    map.put("flag", flag);
	    return map;
	}

	// 이메일 초기 인증
	@ResponseBody
	@PostMapping("/email")
	public Map email(String email) {
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		Properties prop = new Properties();
		mailSenderImpl.setHost("smtp.gmail.com");
		mailSenderImpl.setPort(587);
		mailSenderImpl.setUsername("plantifultest@gmail.com");
		mailSenderImpl.setPassword("pwrapugvstauftfe");
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", true);
		mailSenderImpl.setJavaMailProperties(prop);
		Map map = new HashMap<>();
		MemberDto dto = service.getMember(email);
		if (dto != null) {
			map.put("exist", "이미 존재하는 이메일입니다.");
		} else {
			if (!isValidEmail(email)) {
				map.put("message", "이메일 형식이 올바르지 않습니다.");
				return map;
			}
			Random random = new Random(); // 난수 생성을 위한 랜덤 클래스
			String key = ""; // 인증번호

			SimpleMailMessage message = new SimpleMailMessage(); // 이메일 제목, 내용 작업 메서드
			message.setFrom("workjuwon92@gmail.com");
			message.setTo(email); // 스크립트에서 보낸 메일을 받을 사용자 이메일 주소
			// 입력 키를 위한 코드
			// 난수 생성
			for (int i = 0; i < 3; i++) {
				int index = random.nextInt(26) + 65;
				key += (char) index;
			}
			for (int i = 0; i < 6; i++) {
				int numIndex = random.nextInt(10);
				key += numIndex;
			}

			String mail = "\n Plantiful 회원가입 이메일 인증입니다.\n";
			message.setSubject("Plantiful 회원가입을 위한 인증번호 전송 메일입니다.\n"); // 이메일 제목
			message.setText( mail + "인증번호는 " + key + " 입니다."); // 이메일 내용
			try {
				mailSenderImpl.send(message); // 이메일 전송
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("key", key);
			map.put("dto", dto);

		}
		return map;
	}

	private boolean isValidEmail(String email) {
		return email.contains("@");
	}

	// 비밀번호 재설정 메일
	@ResponseBody
	@PostMapping("/emailpwdcheck")
	public Map pwdcheck(String email) {
		System.out.println("###############이메일인증" + email);
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		Properties prop = new Properties();
		mailSenderImpl.setHost("smtp.gmail.com");
		mailSenderImpl.setPort(587);
		mailSenderImpl.setUsername("plantifultest@gmail.com");
		mailSenderImpl.setPassword("pwrapugvstauftfe");
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", true);
		mailSenderImpl.setJavaMailProperties(prop);
		Map map = new HashMap<>();
		MemberDto dto = service.getMember(email);
		if (dto == null) {
			map.put("exist", "존재하지 않는 이메일 입니다.");
		} else {
			if (!isValidEmail(email)) {
				map.put("message", "이메일 형식이 올바르지 않습니다.");
				return map;
			}
			Random random = new Random(); // 난수 생성을 위한 랜덤 클래스
			String key = ""; // 인증번호

			SimpleMailMessage message = new SimpleMailMessage(); // 이메일 제목, 내용 작업 메서드
			message.setTo(email); // 스크립트에서 보낸 메일을 받을 사용자 이메일 주소
			// 입력 키를 위한 코드
			// 난수 생성
			for (int i = 0; i < 3; i++) {
				int index = random.nextInt(26) + 65;
				key += (char) index;
			}

			for (int i = 0; i < 6; i++) {
				int numIndex = random.nextInt(10);
				key += numIndex;
			}

			String mail = "\n Plantiful 비밀번호 재 설정 인증 메일입니다.\n";
			message.setSubject("Plantiful 임시비밀번호 전송 메일입니다."); // 이메일 제목
			message.setText(mail + "임시비밀번호는 " + key + " 입니다."); // 이메일 내용
			try {
				mailSenderImpl.send(message);
				System.out.println(email + key);
				newPassword(email, key);
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("key", key);
			map.put("dto", dto);

		}
		return map;
	}

	// 본인인증완료 후 빼내 온 정보 중 핸드폰 번호와 입력한 핸드폰 번호가 일치한지 확인하고
	// 일치하면 true 아니면 false 를 반환해서 완벽하게 이루어 졌나 확인 한다.
	@GetMapping("/certifications/redirect")
	public Map handleRedirect(@RequestParam("imp_uid") String impUid, @RequestParam("email") String email) {
//		boolean flag = false;
		Map map = new HashMap<>();
		System.out.println("Received imp_uid: " + impUid);
		map = checkinfoservice.getAccessToken(impUid);
		MemberDto dto = service.getMember(email);
	
		String phone = (String) map.get("phone");
		String name = (String) map.get("name");

//		if (dto != null) { // 가입이 이미 되어 있고 정
//			if (dto.getPhone().equals(phone)) {
//				flag = true;
//			}
//		}
//		map.put("flag", flag);
		
		map.put("name", name);
		map.put("phone", phone);
		return map;
	}

	// 카카오톡 로그인 정보 빼오기
	@GetMapping("/getKakaomember/{email}")
	public Map get(@PathVariable("email") String email) {
		Map map = new HashMap();
		boolean flag = false; // 존재하지 않는 회원
		MemberDto d = service.getMember(email);
		System.out.println(d);
		if (d != null) {// dto가 null 이 아니라면
			flag = true; // 존재하는 회원
			map.put("dto", d);
			map.put("flag", flag);
		} else {
			map.put("messeage", "존재하지 않는 회원이니 회원가입해라");
			map.put("flag", flag);
		}
		System.out.println("flag = " + flag);
		return map;
	}
	
	@GetMapping("/certifications/checkAccount")
	public Map CheckAccount(@RequestParam("bank_code") String bank_code, @RequestParam("bank_num") String bank_num) {
		Map map = new HashMap<>();
		map = checkinfoservice.getAccessToken1(bank_code, bank_num);
	
		String bankHolderInfo = (String) map.get("bankHolderInfo");
		
		Object errorObj = map.get("error");
		if (errorObj instanceof String) {
		    String errorStr = (String) errorObj;
		    int error1 = Integer.parseInt(errorStr);
		    map.put("errormsg", String.valueOf(error1));
		} 

		
		map.put("bankHolderInfo", bankHolderInfo);
		return map;
	}

}
