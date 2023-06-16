package com.example.demo.schedulegroup;

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

@RestController // rest api controller
@CrossOrigin(origins = "*") // 모든 ip로부터 요청 받기 허용
@RequestMapping("/schedulegroup") // 기본 url: restshop
public class ScheduleGroupController {
	@Autowired
	private ScheduleGroupService service;

	// 그룹목록
	@GetMapping("")
	public Map getAll() {
		ArrayList<ScheduleGroupDto> list = service.getAll();
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}

	// 그룹추가
	@PostMapping("")
	public Map add(ScheduleGroupDto dto) {
		ScheduleGroupDto sg = service.save(dto);
		Map map = new HashMap();
		map.put("dto", sg);
		return map;
	}

	// 그룹 상세
	@GetMapping("/{schedulegroup_num}")
	public Map getByNum(@PathVariable("schedulegroup_num") int schedulegroup_num) {
		ScheduleGroupDto dto = service.getGroup(schedulegroup_num);
		Map map = new HashMap();
		map.put("dto", dto);
		return map;
	}

	// 그룹삭제
	@DeleteMapping("/{schedulegroup_num}")
	public Map deleteGroup(@PathVariable("schedulegroup_num") int schedulegroup_num) {
		Map map = new HashMap();
		boolean flag = true;
		try {
			service.DelGroup(schedulegroup_num);
		} catch (Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		return map;
	}

}
