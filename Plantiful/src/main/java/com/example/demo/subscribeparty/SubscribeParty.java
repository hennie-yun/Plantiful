package com.example.demo.subscribeparty;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.member.Member;
import com.example.demo.schedule.Schedule;
import com.example.demo.subscribeboard.SubscribeBoard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeParty {

	@Id
	@SequenceGenerator(name="seq_subparty", sequenceName="seq_subparty", allocationSize=1)//시퀀스 생성. sequenceName:시퀀스 이름
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_subparty")//값 자동생성설정
	private int party;
	@ManyToOne
	@JoinColumn(name="subscribeNum", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE) //만약 board에서 본인이삭제하면 걍 아예 안뜨는걸로 
	private SubscribeBoard subscribeNum;
	@ManyToOne
	@JoinColumn(name="email", nullable=true)
	private Member email;
	private int point_basket;
	private Date enddate;
	@Column(name = "start_check")
	private int startcheck;
	@OneToOne
	@JoinColumn(name="schedule_num", nullable=true)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Schedule schedule_num;
	
}
