package com.example.demo.invite;

import com.example.demo.member.Member;
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
public class InviteDto {
	private int invitenum; 
	private ScheduleGroup groupnum; 
	private Member email; 
}
