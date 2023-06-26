package com.example.demo.payment;

import com.example.demo.member.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

	private String impuid;
	private String merchantuid;
	private int paidamount;
	private int applynum;
	private Member email;
	
}
