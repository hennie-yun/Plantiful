package com.example.demo.subscribeparty;

import java.util.Date;

import com.example.demo.member.Member;
import com.example.demo.schedule.Schedule;
import com.example.demo.subscribeboard.SubscribeBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubscribePartyDto {
	private int party;
	private SubscribeBoard subscribe_num;
	private Member email;
	private int point_basket;
	private Date enddate;
	private int start_check;
	private Schedule schedule_num;
}
