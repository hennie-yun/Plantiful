package com.example.demo.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.demo.member.Member;
import com.example.demo.schedulegroup.ScheduleGroup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ScheduleDto {
	private int schedule_num;
	private ScheduleGroup group_num;
	private Member email;
	private String title;
	private LocalDate startDate;
	private LocalDate endDate;
	private String startTime;
	private String endTime;
	private String info;
	private LocalTime alert;
	private int isLoop;
	private int day;
}
