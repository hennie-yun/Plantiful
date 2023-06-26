package com.example.demo.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

	@Autowired
	private MemberDao dao;

	// 내정보수정
	public MemberDto edit(MemberDto dto) {
			Member entity = dao.save(new Member(dto.getEmail(),dto.getPwd(),dto.getNickname(),dto.getPhone(),dto.getId(),dto.getImg()));
			return new MemberDto(entity.getEmail(),entity.getPwd(),entity.getNickname(),entity.getPhone(),entity.getId(),entity.getImg(),null);
		}
	
	public String save(MemberDto dto) {
		Member entity = dao.save(new Member(dto.getEmail(), dto.getPwd(), dto.getNickname(), dto.getPhone(),
				dto.getId(),dto.getImg()));
		return entity.getEmail();
	}
	

	// 로그인, 내정보확인
	public MemberDto getMember(String email) {
		Member entity = dao.findById(email).orElse(null);
		if (entity == null) {
			return null;
		}
		return new MemberDto(entity.getEmail(), entity.getPwd(), entity.getNickname(), entity.getPhone(),
				entity.getId(),entity.getImg(), null);
	}
	
	
	//카카오로 회원가입 했을 대 쓸거임 
	public void saveMemberKaKao(String email, long id, String nickname) {   
	        Member entity = dao.save(new Member(email, null, nickname, null, id, null));	     
	}

	// 탈퇴
	public void delMember(String email) {
		dao.deleteById(email);
	}

}
