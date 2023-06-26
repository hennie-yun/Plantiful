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
	private long id; //카카오 전용 
	
	@Column(nullable = true)
	private String img;
	

}
