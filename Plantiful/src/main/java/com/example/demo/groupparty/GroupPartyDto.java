package com.example.demo.groupparty;

import com.example.demo.schedule.Schedule;
import com.example.demo.schedulegroup.ScheduleGroup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GroupPartyDto {
	private int groupparty_num;
	private ScheduleGroup group_num;
	private Schedule schedule_email;
}
