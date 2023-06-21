package com.example.demo.schedulegroup;

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
public class ScheduleGroup {
	@Id
	@SequenceGenerator(name = "seq_gen", sequenceName = "seq_schedulegroup_num", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_schedulegroup_num")
	private int schedulegroup_num;
	private String schedulegroup_title;
	private int schedulegroup_color;
}
