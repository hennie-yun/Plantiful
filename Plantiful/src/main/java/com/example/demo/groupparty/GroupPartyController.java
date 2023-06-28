package com.example.demo.groupparty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.schedulegroup.ScheduleGroup;
import com.example.demo.schedulegroup.ScheduleGroupService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/groupparty")
public class GroupPartyController {
	@Autowired
	private GroupPartyService service;

	@Autowired
	private ScheduleGroupService scheduleservice;

	// 그룹 가입
	@PostMapping("")
	public Map join(GroupPartyDto dto) {
		GroupPartyDto gp = service.save(dto);
		Map map = new HashMap();
		map.put("dto", gp);
		return map;
	}

	// 전체 검색
	@GetMapping("")
	public Map getAll() {
		ArrayList<GroupPartyDto> list = service.getAll();
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}

	// email로 검색
	@GetMapping("email/{email}")
	public Map getByEmail(@PathVariable("email") String email) {
		ArrayList<GroupPartyDto> list = service.getByEmail(email);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}

	// 그룹 삭제
	@DeleteMapping("/{groupparty_num}")
	public Map outGroup(@PathVariable("groupparty_num") int groupparty_num) {
		boolean flag = true;
		Map map = new HashMap();
		try {
			ScheduleGroup num = service.getSchedulenum(groupparty_num);
			ArrayList<GroupPartyDto> group = service.getGroupPartynum(num.getSchedulegroup_num());
			if (group.size() == 1) {
				map.put("schedule_num", group);
				service.outParty(groupparty_num);
				int schedulenum = num.getSchedulegroup_num();
				ScheduleGroup schedule = new ScheduleGroup(schedulenum, null, 0);
				scheduleservice.DelGroup(schedule.getSchedulegroup_num());
			}
		} catch (Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		return map;
	}
}
