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
	private Date register_date; //test1
	private Date recruit_endperiod; //test2
	private Date payment_date; //test3
	private Date subscribe_startdate; //test4
	private Date subscribe_enddate; //test5
	private int add_check; //party에 추가했는지 여부확인
//	private String test1;
//	private String test2;
//	private String test3;
//	private String test4;
//	private String test5;
//	
////	@SuppressWarnings("deprecation")
//	public void makeDate() {
////		register_date = new Date(test1);
//		recruit_endperiod = new Date(test2);
//		payment_date = new Date(test3);
//		subscribe_startdate = new Date(test4);
//		subscribe_enddate = new Date(test5);
//	}
}
