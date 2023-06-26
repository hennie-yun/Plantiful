package com.example.demo.payment;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.member.Member;

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
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
	
	@Id
	private String impuid;
	private String merchantuid;
	private int paidamount;
	private int applynum;
	
	@ManyToOne
	@JoinColumn(name="email", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member email; 
	

}
