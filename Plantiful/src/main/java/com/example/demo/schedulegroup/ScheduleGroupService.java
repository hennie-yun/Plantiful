package com.example.demo.schedulegroup;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleGroupService {
	@Autowired
	private ScheduleGroupDao dao;

	// 그룹 생성
	public ScheduleGroupDto save(ScheduleGroupDto dto) {
		ScheduleGroup entity = dao.save(new ScheduleGroup(dto.getSchedulegroup_num(), dto.getSchedulegroup_title(),
				dto.getSchedulegroup_color()));
		return new ScheduleGroupDto(entity.getSchedulegroup_num(), entity.getSchedulegroup_title(),
				entity.getSchedulegroup_color());
	}

	// 그룹 삭제
	public void DelGroup(int num) {
		dao.deleteById(num);
	}

	// 그룹번호로 검색(그룹 상세)
	public ScheduleGroupDto getGroup(int schedulegroup_num) {
		ScheduleGroup s = dao.findById(schedulegroup_num).orElse(null);
		if (s == null) {
			return null;
		}
		return new ScheduleGroupDto(s.getSchedulegroup_num(), s.getSchedulegroup_title(), s.getSchedulegroup_color());
	}

	// 그룹목록
	public ArrayList<ScheduleGroupDto> getAll() {
		ArrayList<ScheduleGroup> list = (ArrayList<ScheduleGroup>) dao.findAll();
		ArrayList<ScheduleGroupDto> list2 = new ArrayList<ScheduleGroupDto>();
		for (ScheduleGroup sg : list) {
			list2.add(new ScheduleGroupDto(sg.getSchedulegroup_num(), sg.getSchedulegroup_title(),
					sg.getSchedulegroup_color()));
		}
		return list2;
	}
}
