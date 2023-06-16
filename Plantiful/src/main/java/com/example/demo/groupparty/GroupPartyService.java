package com.example.demo.groupparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupPartyService {
	@Autowired
	private GroupPartyDao dao;

	// 그룹 가입
	public GroupPartyDto save(GroupPartyDto dto) {
		GroupParty entity = dao
				.save(new GroupParty(dto.getGroupparty_num(), dto.getSchedulegroup_num(), dto.getMember_email()));
		return new GroupPartyDto(entity.getGroupparty_num(), entity.getSchedulegroup_num(), entity.getMember_email());
	}

	// 탈퇴
	public void outParty(int groupparty_num) {
		dao.deleteById(groupparty_num);
	}

	public GroupPartyDto getGroupPartynum(int schedulegroup_num) {
		GroupParty entity = dao.findByScheduleGroupnum(schedulegroup_num);
		if (entity == null) {
			return null;
		}
		return new GroupPartyDto(entity.getGroupparty_num(), entity.getSchedulegroup_num(), entity.getMember_email());
	}

}
