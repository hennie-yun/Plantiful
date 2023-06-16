package com.example.demo.chat.dto;

import java.sql.Timestamp;

import com.example.demo.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
	// 메세지 타입 : 입장, 채팅, 퇴장
	// 메세지 타입에 따라서 동작하는 구조가 달라짐
	// 입장(ENTER)과 퇴장(LEAVE)의 경우 입장 / 퇴장 이벤트 처리가 실행 
	// TALK는 내용이 sub하고 있는 모든 클라이언트에게 전달 된다 
	public enum MessageType {
		ENTER, TALK, LEAVE;
	}
	
	private MessageType type;	// 메세지 타입
	private long num;
	private String roomId;
	private Member member;
	private String message;
	private Timestamp time;
	private boolean isRequest;
}