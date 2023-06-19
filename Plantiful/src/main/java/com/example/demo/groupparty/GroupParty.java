package com.example.demo.groupparty;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.member.Member;
import com.example.demo.schedulegroup.ScheduleGroup;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private int groupparty_num;

	@ManyToOne
	@JoinColumn(name = "schedulegroup_num", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE) // 만약 board에서 본인이삭제하면 걍 아예 안뜨는걸로
	private ScheduleGroup scheduleGroupNum;

	@ManyToOne
	@JoinColumn(name = "member_email", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE) // 만약 board에서 본인이삭제하면 걍 아예 안뜨는걸로
	private Member member_email;

}
