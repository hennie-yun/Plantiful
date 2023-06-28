package com.example.demo.chat;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatDao extends JpaRepository<Chat, Long>{
	ArrayList<Chat> findByRoom(ChatRoom room);
	ArrayList<Chat> findAllByRoomNumOrderBySendTime(long roomNum);
}
