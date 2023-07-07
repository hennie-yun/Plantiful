package com.example.demo.chat;

import java.sql.Timestamp;

import com.example.demo.member.Member;
import com.example.demo.subscribeboard.SubscribeBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
	private long num;
	private String lastMsg;
	private Timestamp lastSendTime;
	private Member lastSender;
	private SubscribeBoard subscribeNum;
}
