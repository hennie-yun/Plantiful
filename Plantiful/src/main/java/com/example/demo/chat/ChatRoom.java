package com.example.demo.chat;

import com.example.demo.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "seq_room", sequenceName = "seq_room")
public class ChatRoom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_room")
	private long num;
	private String lastMsg;
	
	@ManyToOne
	@JoinColumn(name = "email")
	private Member lastSender;
	private long userCount;
	
}
