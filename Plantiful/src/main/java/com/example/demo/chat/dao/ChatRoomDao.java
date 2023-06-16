package com.example.demo.chat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.chat.dto.ChatRoom;

public interface ChatRoomDao extends JpaRepository<ChatRoom, String> {

}
