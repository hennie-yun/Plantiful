package com.example.demo.schedulegroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleGroupService {
	@Autowired
	private ScheduleGroupDao dao;

	// 그룹 생성
	public ScheduleGroupDto save(ScheduleGroupDto dto) {
		ScheduleGroup entity = dao
				.save(new ScheduleGroup(dto.getGroup_num(), dto.getGroup_title(), dto.getGroup_color()));
		return new ScheduleGroupDto(entity.getGroup_num(), entity.getGroup_title(), entity.getGroup_color());
	}

	// 그룹 삭제
	public void DelGroup(int num) {
		dao.deleteById(num);
	}

	// 그룹번호로 검색
	public ScheduleGroupDto getGroup(int num) {
		ScheduleGroup s = dao.findById(num).orElse(null);
		if (s == null) {
			return null;
		}
		return new ScheduleGroupDto(s.getGroup_num(), s.getGroup_title(), s.getGroup_color());
	}
}
