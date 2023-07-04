package com.example.demo.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.JwtTokenProvider;

@RestController // rest api controller
@CrossOrigin(origins = "*") // 모든 ip로부터 요청 받기 허용
@RequestMapping("/schedules") // 기본 url: restshop
public class ScheduleContorller {
	@Autowired
	private ScheduleService service;

	@Autowired(required = false)
	private JwtTokenProvider tokenProvider;

	// 스케줄 추가
	@PostMapping("")
	public Map add(ScheduleDto dto) {
		ScheduleDto d = service.save(dto);
		Map map = new HashMap();
		map.put("dto", d);
		return map;
	}

	// 스케줄 검색
	@GetMapping("schedule_num/{schedule_num}")
	public Map getBySchedulenum(@PathVariable("schedule_num") int schedule_num) {
		ScheduleDto dto = service.getSchedule(schedule_num);
		Map map = new HashMap();
		map.put("dto", dto);
		return map;
	}

	// 토큰값으로 정보 불러오기
	@GetMapping("")
	public Map getInfo(@RequestHeader("token") String token) {
		boolean flag = true;
		Map map = new HashMap();
		try {
			String email = tokenProvider.getUsernameFromToken(token);
			map.put("email", email);
		} catch (Exception e) {
			flag = false;
		}
		map.put("flag", flag);
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
		ArrayList<ScheduleDto> list = service.getByEmail(email);
		System.out.println(list);
		map.put("list", list);
		return map;
	}

	// 그룹 검색
	@GetMapping("group_num/{group_num}")
	public Map getByGroupNum(@PathVariable("group_num") int group_num) {
		ArrayList<ScheduleDto> list = service.getByGroupnum(group_num);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}

	// 시작날짜 검색
	@GetMapping("startdate/{startDate}")
	public Map getByStartDate(@PathVariable("startDate") String startDate) {
		ArrayList<ScheduleDto> list = service.getByStartDate(startDate);
		Map map = new HashMap();
		map.put("list", list);
		System.out.println("날짜검색" + list);
		return map;
	}

//	// 전체검색
//	@GetMapping("")
//	public Map getAll() {
//		ArrayList<ScheduleDto> list = service.getall();
//		Map map = new HashMap();
//		map.put("list", list);
//		return map;
//	}

	// 스케줄 수정
	@PutMapping("")
	public Map edit(ScheduleDto dto) {
		ScheduleDto old = service.getSchedule(dto.getSchedule_num());
		old.setTitle(dto.getTitle());
		old.setStart(dto.getStart());
		old.setEnd(dto.getEnd());
		old.setStartTime(dto.getStartTime());
		old.setEndTime(dto.getEndTime());
		old.setInfo(dto.getInfo());
		old.setAlert(dto.getAlert());
		old.setIsLoop(dto.getIsLoop());
		old.setDay(dto.getDay());
		ScheduleDto s = service.save(old);
		Map map = new HashMap();
		map.put("dto", s);
		return map;
	}

	@PatchMapping("resize/{schedule_num}/{start}/{end}")
	public Map edit(@PathVariable("schedule_num") int schedule_num, @PathVariable("start") String start,
			@PathVariable("end") String end) {
		System.out.println("하윙");
		ScheduleDto old = service.getSchedule(schedule_num);
		old.setStart(start);
		old.setEnd(end);
		System.out.println("날짜 길이" + start + end);
		ScheduleDto s = service.save(old);
		Map map = new HashMap();
		map.put("dto", s);
		return map;
	}

	// 스케줄 삭제
	@DeleteMapping("/{schedule_num}")
	public Map del(@PathVariable("schedule_num") int schedule_num) {
		boolean flag = true;
		try {
			service.delSchedule(schedule_num);
		} catch (Exception e) {
			flag = false;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

}
