package com.example.demo.chat;

import java.sql.Timestamp;

import com.example.demo.member.Member;

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
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "seq_chat", sequenceName = "seq_chat")
public class Chat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_chat")
	private long num;
	
	@ManyToOne
	@JoinColumn(name = "room_num")
	private ChatRoom room;
	
	@ManyToOne
	@JoinColumn(name = "email")
	private Member member;
	private String message;
	private Timestamp sendTime;
	private boolean isRequest;
}
