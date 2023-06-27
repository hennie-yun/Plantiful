package com.example.demo.todoList;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.example.demo.member.Member;

import jakarta.persistence.Column;
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
	@JoinColumn(name = "email")
	private String email;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "email")
	@Cascade(CascadeType.ALL)	
	private Member member;
	
	@Column(nullable = true)
	private String text;
}
