package com.example.demo.chat;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.example.demo.chat.dto.ChatRoomDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomMap {
    private static ChatRoomMap chatRoomMap = new ChatRoomMap();
    private ConcurrentMap<String, ChatRoomDto> chatRooms = new ConcurrentHashMap<>();

    public static ChatRoomMap getInstance(){
        return chatRoomMap;
    }

}
