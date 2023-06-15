package com.example.demo.schedule;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
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
	private int group_num;
	private String schedule_email;
	private String schedule_title;
	private Date schedule_startdate;
	private Date schedule_enddate;
	private Timestamp schedule_starttime;
	private Timestamp scehdule_endtime;
	private String schedule_info;
	private Timestamp schedule_alert;
	private int isLoop;
	private int day;
}
