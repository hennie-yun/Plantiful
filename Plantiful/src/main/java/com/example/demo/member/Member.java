package com.example.demo.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Member {
	
	@Id
	private String email;
	private String pwd;
	private String nickname;
	private String phone;
	//조인 해야하나? 
	private int cash;
	@Column(nullable = true)
	private String img;
	

}
