package com.example.demo.schedule;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.member.Member;
import com.example.demo.schedulegroup.ScheduleGroup;

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

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Schedule {
	@Id
	@SequenceGenerator(name="seq_gen", sequenceName = "seq_schedule", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_schedule")
	private int schedule_num;
	
	@ManyToOne
	@JoinColumn(name="group_num", nullable= false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private ScheduleGroup group_num;
	
	@ManyToOne
	@JoinColumn(name="email", nullable= false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Member schedule_email;
	
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
