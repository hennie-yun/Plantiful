package com.example.demo.alarm;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.schedule.Schedule;

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
	@ManyToOne
	@JoinColumn(name="alert", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE) //만약 board에서 본인이삭제하면 걍 아예 안뜨는걸로 
	private Schedule alert;
	@ManyToOne
	@JoinColumn(name="info", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE) //만약 board에서 본인이삭제하면 걍 아예 안뜨는걸로 
	private Schedule info;
}
