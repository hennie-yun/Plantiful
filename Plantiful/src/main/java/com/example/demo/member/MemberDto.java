package com.example.demo.member;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
	
	private String email;
	private String pwd;
	private String nickname;
	private String phone;
	private long id;
	private String img;
	private MultipartFile f;
	


}
