package com.example.demo.groupparty;

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
public class GroupParty {
	@Id
	@SequenceGenerator(name = "seq_gen", sequenceName = "seq_groupparty_num", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_groupparty_num")
	private int groupparty_num;

	@ManyToOne
	@JoinColumn(name = "schedulegroup_num", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE) // 만약 board에서 본인이삭제하면 걍 아예 안뜨는걸로
	private ScheduleGroup schedulegroupnum;

	@ManyToOne
	@JoinColumn(name = "member_email", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE) // 만약 board에서 본인이삭제하면 걍 아예 안뜨는걸로
	private Member memberEmail;

}
