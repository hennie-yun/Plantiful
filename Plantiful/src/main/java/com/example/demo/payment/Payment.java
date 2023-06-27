package com.example.demo.payment;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.member.Member;

import jakarta.persistence.Column;
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
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
	
	@Id
	@SequenceGenerator(name="seq_paymentnum", sequenceName="seq_paymentnum", allocationSize=1, initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_paymentnum")
	private int paymentnum; 

	private String impuid; 
	private String merchantuid;
	private int paidamount;
	private int applynum;
	
	@ManyToOne
	@JoinColumn(name="email", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member email; 
	

}
