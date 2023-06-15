package com.example.demo.schedulegroup;

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
public class ScheduleGroupDto {
	private int group_num;
	private String group_title;
	private int group_color;
}
