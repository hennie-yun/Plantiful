package com.example.demo.groupparty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.jni.Sockaddr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.JwtTokenProvider;
import com.example.demo.member.Member;
import com.example.demo.schedulegroup.ScheduleGroup;
import com.example.demo.schedulegroup.ScheduleGroupDto;
import com.example.demo.schedulegroup.ScheduleGroupService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/groupparty")
public class GroupPartyController {
	@Autowired
	private GroupPartyService service;

	@Autowired
	private ScheduleGroupService schedulegroupservice;

	@Autowired(required = false)
	private JwtTokenProvider tokenProvider;

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
	public Map getByEmail(@PathVariable("email") String email,
			@RequestHeader(name = "token", required = false) String token) {
		Map map = new HashMap();
		if (token != null) {
			try {
				String id = tokenProvider.getUsernameFromToken(token);
				if (!email.equals(id)) {
					map.put("dto", null);
					return map;
				}
			} catch (Exception e) {
				map.put("dto", null);
				return map;
			}
		}

		ArrayList<GroupPartyDto> list = service.getByEmail(email);
		System.out.println(list);
		map.put("list", list);
		return map;
	}

//	 @GetMapping("/schedulegroup/title")
//	    public ResponseEntity<String> getScheduleGroupTitleByMemberAndScheduleGroupNum(@RequestParam("email") String email, @RequestParam("scheduleGroupNum") int scheduleGroupNum) {
//	        Member member = new Member();
//	        member.setEmail(email);
//	        ScheduleGroupDto scheduleGroup = schedulegroupservice.getGroup(scheduleGroupNum);
//	        String scheduleGroupTitle = service.getScheduleGroupTitleByMemberAndScheduleGroupNum(email, scheduleGroup);
//	        return ResponseEntity.ok(scheduleGroupTitle);
//	    }

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
				schedulegroupservice.DelGroup(schedule.getSchedulegroup_num());
			}
		} catch (Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		return map;
	}
}
