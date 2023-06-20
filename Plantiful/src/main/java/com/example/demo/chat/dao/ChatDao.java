package com.example.demo.chat.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.chat.dto.Chat;
import com.example.demo.chat.dto.ChatRoom;

@Repository
public interface ChatDao extends JpaRepository<Chat, Long>{
	ArrayList<Chat> findByRoom(ChatRoom room);
	ArrayList<Chat> findAllByRoomNumOrderBySendTime(long roomNum);
}
