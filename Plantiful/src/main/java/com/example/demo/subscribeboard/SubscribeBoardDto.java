package com.example.demo.subscribeboard;

import java.util.Date;

import com.example.demo.member.Member;

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
public class SubscribeBoardDto {
	private int subscribe_num;
	private Member email;
	private String title;
	private String site;
	private int total_point;
	private int total_people;
	private Date register_date;
	private Date recruit_endperiod;
	private Date payment_date;
	private Date subscribe_startdate;
	private Date subscribe_enddate;
	private int add_check; //party에 추가했는지 여부확인
}
