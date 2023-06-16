package com.example.demo.chat.dto;

import java.util.concurrent.ConcurrentHashMap;

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
public class ChatRoomDto {

	// Stomp 를 통해 pub/sub 를 사용하면 구독자 관리가 알아서 처리
	// 따라서 따로 세션 관리를 하는 코드를 작성할 필도 없음
	// 메시지를 다른 세션의 클라이언트에게 발송하는 것도 구현 필요가 없음

	private String roomId;		// 채팅방 아이디
	private String roomName;	// 채팅방 이름
	private long userCount;		// 채팅방 인원수
	private long maxCount;
		
	public ConcurrentHashMap<String, ?> userList = new ConcurrentHashMap<>();
}
