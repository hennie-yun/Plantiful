package com.example.demo.schedule;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.member.Member;
import com.example.demo.schedulegroup.ScheduleGroup;

@Service
public class ScheduleService {
	@Autowired
	private ScheduleDao dao;

	// 추가,수정
	public ScheduleDto save(ScheduleDto dto) {
		Schedule entity = dao.save(new Schedule(dto.getSchedule_num(), dto.getGroup_num(), dto.getEmail(),
				dto.getTitle(), dto.getStart(), dto.getEnd(), dto.getStartTime(), dto.getEndTime(), dto.getInfo(),
				dto.getAlert(), dto.getIsLoop(), dto.getDay()));
		return new ScheduleDto(entity.getSchedule_num(), entity.getGroupnum(), entity.getEmail(), entity.getTitle(),
				entity.getStartDate(), entity.getEndDate(), entity.getStartTime(), entity.getEndTime(),
				entity.getInfo(), entity.getAlert(), entity.getIsLoop(), entity.getDay());

	}

	// 삭제
	public void delSchedule(int schedule_num) {
		dao.deleteById(schedule_num);
	}

	// 스케줄 검색
	public ScheduleDto getSchedule(int schedule_num) {
		Schedule d = dao.findById(schedule_num).orElse(null);
		if (d == null) {
			return null;
		}
		return new ScheduleDto(d.getSchedule_num(), d.getGroupnum(), d.getEmail(), d.getTitle(), d.getStartDate(),
				d.getEndDate(), d.getStartTime(), d.getEndTime(), d.getInfo(), d.getAlert(), d.getIsLoop(), d.getDay());
	}

	// 스케줄 전체
	public ArrayList<ScheduleDto> getall() {
		ArrayList<Schedule> list = (ArrayList<Schedule>) dao.findAll();
		ArrayList<ScheduleDto> list2 = new ArrayList<ScheduleDto>();
		for (Schedule s : list) {
			list2.add(new ScheduleDto(s.getSchedule_num(), s.getGroupnum(), s.getEmail(), s.getTitle(),
					s.getStartDate(), s.getEndDate(), s.getStartTime(), s.getEndTime(), s.getInfo(), s.getAlert(),
					s.getIsLoop(), s.getDay()));
		}
		return list2;
	}

	// email로 검색
	public ArrayList<ScheduleDto> getByEmail(String email) {
		Member m = new Member(email, "", "", "", 0, "");
		ArrayList<Schedule> list = (ArrayList<Schedule>) dao.findByemail(m);
		ArrayList<ScheduleDto> list2 = new ArrayList<ScheduleDto>();
		for (Schedule s : list) {
			list2.add(new ScheduleDto(s.getSchedule_num(), s.getGroupnum(), s.getEmail(), s.getTitle(),
					s.getStartDate(), s.getEndDate(), s.getStartTime(), s.getEndTime(), s.getInfo(), s.getAlert(),
					s.getIsLoop(), s.getDay()));
		}
		return list2;
	}
	// 스케줄title로 스케줄이 같은 email조회
		public String getMemberEmail(ScheduleDto dto) {
			ArrayList<Schedule> list = dao.findBytitle(dto.getTitle());
			String names = "";
			for(Schedule s : list) {
				names += s.getEmail()+" ";
			}
			System.out.println(names);
			return names;
		}

	// groupnum으로 검색
	public ArrayList<ScheduleDto> getByGroupnum(int group_num) {
		ScheduleGroup sg = new ScheduleGroup(group_num, "", 0);
		ArrayList<Schedule> list = (ArrayList<Schedule>) dao.findBygroupnum(sg);
		ArrayList<ScheduleDto> list2 = new ArrayList<ScheduleDto>();
		for (Schedule s : list) {
			list2.add(new ScheduleDto(s.getSchedule_num(), s.getGroupnum(), s.getEmail(), s.getTitle(),
					s.getStartDate(), s.getEndDate(), s.getStartTime(), s.getEndTime(), s.getInfo(), s.getAlert(),
					s.getIsLoop(), s.getDay()));
		}
		return list2;
	}
	// 시작날짜로 검색
		public ArrayList<ScheduleDto> getByStartDate(String startDate) {
			ArrayList<Schedule> list = (ArrayList<Schedule>) dao.findByStartDate(startDate);
			ArrayList<ScheduleDto> list2 = new ArrayList<ScheduleDto>();
			for (Schedule s : list) {
				list2.add(new ScheduleDto(s.getSchedule_num(), s.getGroupnum(), s.getEmail(), s.getTitle(),
						s.getStartDate(), s.getEndDate(), s.getStartTime(), s.getEndTime(), s.getInfo(), s.getAlert(),
						s.getIsLoop(), s.getDay()));
			}
			return list2;
		}
}
