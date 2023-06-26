package com.example.demo.schedule;

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
	@SequenceGenerator(name = "seq_gen", sequenceName = "seq_schedule", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_schedule")
	private int schedule_num; // 스케줄 번호

	@ManyToOne
	@JoinColumn(name = "group_num", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ScheduleGroup group_num; // 그룹번호

	@ManyToOne
	@JoinColumn(name = "email", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member email; // 작성자

	private String title; // 스케줄 제목
	private String startDate; // 스케줄 시작일
	private String endDate; // 스케줄 마지막일
	private String  startTime; // 스케줄 시작시간
	private String  endTime; // 스케줄 마지막시간
	private String info; // 스케줄 내용
	private String  alert; // 알림
	private int isLoop; // 반복 유무
	private String day; // 반복일
	
	
//	 public void setTime(LocalTime time) {
//	        this.startTime = time;
//	        this.endTime = time;
//	    }

	// LocalDate (2023-05-14) / LocalTime(07:00:00) 이런 형식으로 들어감
}
