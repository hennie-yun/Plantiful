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
	private int schedulegroup_num;
	private String schedulegroup_title;
	private int schedulegroup_color;
}
