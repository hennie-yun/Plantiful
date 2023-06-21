package com.example.demo.groupparty;

import com.example.demo.member.Member;
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
	public GroupPartyDto(int groupparty_num2, int schedulegroup_num2, String email) {
		// TODO Auto-generated constructor stub
	}
	private int groupparty_num;
	private ScheduleGroup schedulegroup_num;
	private Member member_email;
}
