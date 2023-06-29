package com.example.demo.kakaoaccesstoken;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Kakaotoken {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_seq")
	@SequenceGenerator(name = "token_seq", sequenceName = "token_sequence", allocationSize = 1)
	private int tokennum;
	private String Token;
	private String email;

}
