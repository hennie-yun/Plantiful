package com.example.demo.groupparty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.schedulegroup.ScheduleGroup;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/groupparty")
public class GroupPartyController {
	@Autowired
	private GroupPartyService service;
	
	
	
	
	
	// 그룹 가입
	@PostMapping("")
	public Map join(GroupPartyDto dto) {
		GroupPartyDto gp = service.save(dto);
		Map map = new HashMap();
		map.put("dto", gp);
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
			if(group.size()==1) {
				map.put("schedule_num", group);
				service.outParty(groupparty_num);
			}
		} catch (Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		return map;
	}
}
