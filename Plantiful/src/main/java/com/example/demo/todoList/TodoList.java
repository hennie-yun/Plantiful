package com.example.demo.todoList;

import com.example.demo.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TodoList {
	
	@Id
	private String email;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "member_email", nullable = false)
	private Member member;
	private String text;
}
