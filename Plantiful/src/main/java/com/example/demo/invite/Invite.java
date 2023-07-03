package com.example.demo.invite;

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
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Invite {
	@Id
	@SequenceGenerator(name = "seq_gen", sequenceName = "seq_invitenum", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_invitenum")
	private int invitenum; // 스케줄 번호

	@ManyToOne
	@JoinColumn(name = "groupnum", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ScheduleGroup groupnum; // 그룹번호

	@ManyToOne
	@JoinColumn(name = "email", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member email; // 작성자
}
