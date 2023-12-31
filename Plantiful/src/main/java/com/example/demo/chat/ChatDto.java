package com.example.demo.chat;

import java.sql.Timestamp;

import com.example.demo.member.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
	
	private long num;
	private ChatRoom room;
	private Member member;
	private String message;
	private Timestamp sendTime;
	private boolean isRequest;
}
