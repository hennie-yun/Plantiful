package com.example.demo.chat;

import com.example.demo.member.Member;
import com.example.demo.subscribeparty.SubscribeParty;

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
	private Member lastSender;
	private SubscribeParty subscribeNum;
}
