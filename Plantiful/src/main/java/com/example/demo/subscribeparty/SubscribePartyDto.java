package com.example.demo.subscribeparty;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubscribePartyDto {
	private int party;
	private int subscribe_num;
	private String email;
	private int point_basket;
	private Date remain_month;
	private int start_check;
	private int schedule_num;
}
