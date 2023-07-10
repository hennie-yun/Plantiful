package com.example.demo.naver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class NaverMemberService {
	
	@Autowired
	private NaverMemberDao dao;
	
	// 초기 가입 추가 및 정보가 있다면 token만 수정
	public Navertoken edit(Navertoken navertoken) {
		Navertoken entity = dao.save(new Navertoken(navertoken.getTokennum(), navertoken.getToken(), navertoken.getEmail()));
		return entity;
	}
	
	// 이메일로 찾기
	@Transactional
	public Navertoken findByEmail(String email) {
		Navertoken navertoken = dao.findByEmail(email);
		if(navertoken == null) {
			return null;
		}
		
		return navertoken;
	}
	
	// 우리 토큰을 포함한 db삭제
	public void delNavertoken(String email) {
		Navertoken navertoken = dao.findByEmail(email);
		System.out.println("네에버엑세스토큰에서 del"+navertoken);
		dao.deleteById(navertoken.getTokennum());
	}
}
