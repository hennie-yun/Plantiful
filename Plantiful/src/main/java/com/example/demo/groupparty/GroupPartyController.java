package com.example.demo.groupparty;

import java.lang.reflect.Array;
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
import com.example.demo.schedule.Schedule;
import com.example.demo.schedule.ScheduleDto;
import com.example.demo.schedule.ScheduleService;
import com.example.demo.schedulegroup.ScheduleGroup;
import com.example.demo.schedulegroup.ScheduleGroupService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/groupparty")
public class GroupPartyController {
	@Autowired
	private GroupPartyService service;

	@Autowired
	private ScheduleGroupService schedulegroupservice;
	
	@Autowired
	private ScheduleService scheduleservice;

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

	// schedulegroup_num으로 검색
	@GetMapping("schedulegroup_num/{schedulegroup_num}")
	public Map getByScehdulegroupnum(@PathVariable("schedulegroup_num") int schedulegroup_num) {
		ArrayList<GroupPartyDto> list = service.getByScheduleGroupNum(schedulegroup_num);
		Map map = new HashMap();
		map.put("list", list);
		System.out.println(list);
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
	@DeleteMapping("groupparty_num/{groupparty_num}")
	public Map outGroup(@PathVariable("groupparty_num") int groupparty_num) {
		boolean flag = true;
		Map map = new HashMap();
		try {
			ScheduleGroup num = service.getSchedulenum(groupparty_num);
			ArrayList<GroupPartyDto> group = service.getGroupPartynum(num.getSchedulegroup_num());
			if (group.size() == 1) {
				map.put("schedule_num", group);
				service.outParty(groupparty_num);
				ArrayList<ScheduleDto> num2 = scheduleservice.getByGroupnum(num.getSchedulegroup_num());
				for (ScheduleDto scheduleDto : num2) {
				    int scheduleNum = scheduleDto.getSchedule_num();
				    scheduleservice.delSchedule(scheduleNum);
				    System.out.println(scheduleNum + "번 일정 삭제");
				}
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

//	스케줄 그룹 번호로 스케줄에서 그룹번호와 && 이메일이 같은 list 출력
	//그안에서 for문으로 스케줄 삭제
	// 그룹 나가기
	@DeleteMapping("outparty/{groupparty_num}/{email}")
	public Map outParty(@PathVariable("groupparty_num") int groupparty_num, @PathVariable("email")String email) {
		boolean flag = true;
		 try {
		        ArrayList<ScheduleDto> list = scheduleservice.getByEmail(email); // email로 찾은 스케줄 리스트
		        ScheduleGroup num = service.getSchedulenum(groupparty_num);
		        ArrayList<ScheduleDto> num2 = scheduleservice.getByGroupnum(num.getSchedulegroup_num());

		        // 그룹 번호와 일치하는 스케줄만 삭제
		        ArrayList<Integer> scheduleNumsToDelete = new ArrayList<>();
		        for (ScheduleDto schedule : list) {
		            if (num2.stream().anyMatch(s -> s.getSchedule_num() == schedule.getSchedule_num())) {
		                scheduleNumsToDelete.add(schedule.getSchedule_num());
		            }
		        }

		        for (int scheduleNum : scheduleNumsToDelete) {
		            scheduleservice.delSchedule(scheduleNum);
		            System.out.println(scheduleNum + "번 일정 삭제");
		        }
	        service.outParty(groupparty_num); // 그룹에서 멤버 제거
		} catch (Exception e) {
			flag = false;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}
}
