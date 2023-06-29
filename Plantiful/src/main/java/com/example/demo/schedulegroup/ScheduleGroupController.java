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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.JwtTokenProvider;
import com.example.demo.groupparty.GroupPartyDto;
import com.example.demo.groupparty.GroupPartyService;
import com.example.demo.member.Member;
import com.example.demo.member.MemberService;
import com.example.demo.schedule.Schedule;
import com.example.demo.schedule.ScheduleDto;
import com.example.demo.schedule.ScheduleService;

@RestController // rest api controller
@CrossOrigin(origins = "*") // 모든 ip로부터 요청 받기 허용
@RequestMapping("/schedulegroup") // 기본 url: restshop
public class ScheduleGroupController {
	@Autowired
	private ScheduleGroupService service;
	
	@Autowired
	private GroupPartyService groupservice;

	@Autowired
	private MemberService memberservice;
	@Autowired
	private ScheduleService Scheduleservice;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
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
	public Map add(ScheduleGroupDto dto, @RequestHeader("token") String token) {
		ScheduleGroupDto sg= service.save(new ScheduleGroupDto(0, dto.getSchedulegroup_title(), dto.getSchedulegroup_color()));
		
		String email = tokenProvider.getUsernameFromToken(token);
		Member member = new Member(email, null, null, null, 0, null);
		ScheduleGroup group = new ScheduleGroup(sg.getSchedulegroup_num(), null, 0);
//		ScheduleDto schedule = new ScheduleDto(0,group,member,null,null,null,null,null,null,null,0,null);
		GroupPartyDto party = new GroupPartyDto(0, group, member);
		
		Map map = new HashMap();
		map.put("dto", sg);
		
//		Scheduleservice.save(schedule);
		groupservice.save(party);
		
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
