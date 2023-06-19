package com.example.demo.groupparty;

import java.util.ArrayList;

import javax.swing.GroupLayout.Group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.schedulegroup.ScheduleGroup;

@Service
public class GroupPartyService {
	@Autowired
	private GroupPartyDao dao;

	// 그룹 가입
	public GroupPartyDto save(GroupPartyDto dto) {
		GroupParty entity = dao
				.save(new GroupParty(dto.getGroupparty_num(), dto.getSchedulegroup_num(), dto.getMember_email()));
		return new GroupPartyDto(entity.getGroupparty_num(), entity.getScheduleGroupNum(), entity.getMember_email());
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
		for(GroupParty party :entity) {
			GroupPartyDto dto = new GroupPartyDto(party.getGroupparty_num(), party.getScheduleGroupNum(), party.getMember_email());
			dtoList.add(dto);
		}
		
		return dtoList;
	}

}
