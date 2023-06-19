package com.example.demo.alarm;

import com.example.demo.schedule.Schedule;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {
	@Id
	@SequenceGenerator(name="seq_gen", sequenceName = "seq_alarm", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_alarm")
	private int alarm_num;
	private Schedule shedule_alert;
	private Schedule schedule_info;
}
