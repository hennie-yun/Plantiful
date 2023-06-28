package com.example.demo.groupparty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.GroupLayout.Group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.member.Member;
import com.example.demo.schedulegroup.ScheduleGroup;

import retrofit2.http.GET;

@Service
public class GroupPartyService {
	@Autowired
	private GroupPartyDao dao;

	// 그룹 전체 검색
	public ArrayList<GroupPartyDto> getAll() {
		ArrayList<GroupParty> list = (ArrayList<GroupParty>) dao.findAll();
		ArrayList<GroupPartyDto> list2 = new ArrayList<>();
		for (GroupParty li : list) {
			GroupPartyDto dto = new GroupPartyDto(li.getGroupparty_num(), li.getScheduleGroupNum(),
					li.getMemberEmail());
			list2.add(dto);
		}
		return list2;
	}

	// 이메일로 검색
	public ArrayList<GroupPartyDto> getByEmail(String email) {
		Member m = new Member(email, "", "", "", 0, "");
		ArrayList<GroupParty> list = (ArrayList<GroupParty>) dao.findBymemberEmail(m);
		ArrayList<GroupPartyDto> list2 = new ArrayList<GroupPartyDto>();
		for (GroupParty gp : list) {
			list2.add(new GroupPartyDto(gp.getGroupparty_num(), gp.getScheduleGroupNum(), gp.getMemberEmail()));
		}
		return list2;
	}

	// 그룹파티번호로 그룹스케줄번호 조회
	public ScheduleGroup getSchedulenum(int groupparty_num) {
		GroupParty dto = dao.findById(groupparty_num).orElse(null);
		ScheduleGroup scheduleNum = dto.getScheduleGroupNum();
		return scheduleNum;
	}

	// 그룹 가입
	public GroupPartyDto save(GroupPartyDto dto) {
		GroupParty entity = dao
				.save(new GroupParty(dto.getGroupparty_num(), dto.getSchedulegroup_num(), dto.getMember_email()));
		return new GroupPartyDto(entity.getGroupparty_num(), entity.getScheduleGroupNum(), entity.getMemberEmail());
	}

	// 탈퇴
	public void outParty(int groupparty_num) {
		dao.deleteById(groupparty_num);
	}

	public ArrayList<GroupPartyDto> getGroupPartynum(int schedulegroup_num) {
		ArrayList<GroupParty> entity = dao.findByScheduleGroupNum(new ScheduleGroup(schedulegroup_num, null, 0));
		if (entity == null) {
			return null;
		}
		ArrayList<GroupPartyDto> dtoList = new ArrayList<>();
		for (GroupParty party : entity) {
			GroupPartyDto dto = new GroupPartyDto(party.getGroupparty_num(), party.getScheduleGroupNum(),
					party.getMemberEmail());
			dtoList.add(dto);
		}

		return dtoList;
	}

}
