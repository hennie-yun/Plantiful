package com.example.demo.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // rest api controller
@CrossOrigin(origins = "*") // 모든 ip로부터 요청 받기 허용
@RequestMapping("/schedules") // 기본 url: restshop
public class ScheduleContorller {
	@Autowired
	private ScheduleService service;

	// 스케줄 추가
	@PostMapping("")
	public Map add(ScheduleDto dto) {
		ScheduleDto d = service.save(dto);
		Map map = new HashMap();
		map.put("dto", d);
		return map;
	}

	// 스케줄 검색
	@GetMapping("/{schedule_num}")
	public Map getBySchedulenum(@PathVariable("schedule_num") int schedule_num) {
		ScheduleDto dto = service.getSchedule(schedule_num);
		Map map = new HashMap();
		map.put("dto", dto);
		return map;
	}

	// 전체검색
	@GetMapping("")
	public Map getAll() {
		ArrayList<ScheduleDto> list = service.getall();
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}

	// 스케줄 수정
	@PutMapping("")
	public Map edit(ScheduleDto dto) {
		ScheduleDto old = service.getSchedule(dto.getSchedule_num());
		old.setTitle(dto.getTitle());
		old.setStartDate(dto.getStartDate());
		old.setEndDate(dto.getEndDate());
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
