package com.example.demo.subscribeboard;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.example.demo.listener.subscribeboardlistener;
import com.example.demo.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@EntityListeners(subscribeboardlistener.class)
@DynamicInsert //Default 값을 적용하기 위해 사용하는 어노테이션. insert 시 지정된 default 값을 적용
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeBoard {

	@Id
	@SequenceGenerator(name="seq_subnum", sequenceName="seq_subnum", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_subnum")
	@Column(name = "subscribe_num")
	private int subscribeNum; //게시글 번호
	@ManyToOne
	@JoinColumn(name="email", nullable=true) //null이면 탈퇴한 회원입니다라고 보여주기
//	@OnDelete(action = OnDeleteAction.CASCADE) //이거 지우면 걍 null값으로 나타나나??
	private Member email; //작성자 
	private String title; //제목
	private String site; //사이트 종류
	private int total_point; //전체 금액
	private int total_people; //모집 인원
	
//	@Temporal(TemporalType.TIMESTAMP) //sysdate 넣기 prePersist 연계 
	private Date register_date; //등록일
	private Date recruit_endperiod; //모집 마지막 날짜
	private Date payment_date; //지불일
	private Date subscribe_startdate; //구독 시작날짜
	private Date subscribe_enddate; //구독 끝 날짜
	
	@PrePersist
    public void prePersist() { //sysdate
		register_date = new Date();
    }

}
