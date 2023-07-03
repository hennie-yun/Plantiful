package com.example.demo.invite;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.groupparty.GroupParty;
import com.example.demo.groupparty.GroupPartyDto;
import com.example.demo.member.Member;
import com.example.demo.schedulegroup.ScheduleGroup;

@Service
public class InviteService {
	@Autowired
	public InviteDao dao;

	// 추가,수정
	public InviteDto save(InviteDto dto) {
		Invite entity = dao.save(new Invite(dto.getInvitenum(), dto.getGroupnum(), dto.getEmail()));
		return new InviteDto(entity.getInvitenum(), entity.getGroupnum(), entity.getEmail());
	}

	// 전체검색
	public ArrayList<InviteDto> getall() {
		ArrayList<Invite> list = (ArrayList<Invite>) dao.findAll();
		ArrayList<InviteDto> list2 = new ArrayList<InviteDto>();
		for (Invite i : list) {
			list2.add(new InviteDto(i.getInvitenum(), i.getGroupnum(), i.getEmail()));
		}
		return list2;
	}

	// 이메일로 검색
	public ArrayList<InviteDto> getByEmail(String email) {
		Member m = new Member(email, "", "", "", 0, "");
		ArrayList<Invite> list = (ArrayList<Invite>) dao.findByEmail(m);
		ArrayList<InviteDto> list2 = new ArrayList<InviteDto>();
		for (Invite i : list) {
			list2.add(new InviteDto(i.getInvitenum(), i.getGroupnum(), i.getEmail()));
		}
		return list2;
	}

	// groupnum 검색
	public ArrayList<InviteDto> getByGroupnum(int groupnum) {
		ScheduleGroup sg = new ScheduleGroup(groupnum, "", 0);
		ArrayList<Invite> list = (ArrayList<Invite>) dao.findByGroupnum(sg);
		ArrayList<InviteDto> list2 = new ArrayList<InviteDto>();
		for (Invite i : list) {
			list2.add(new InviteDto(i.getInvitenum(), i.getGroupnum(), i.getEmail()));
		}
		return list2;
	}

	// 삭제
	public void delinvite(int invitenum) {
		dao.deleteById(invitenum);
	}
}
