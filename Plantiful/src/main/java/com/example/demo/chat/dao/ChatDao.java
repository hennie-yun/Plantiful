package com.example.demo.chat.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.chat.dto.Chat;
import com.example.demo.chat.dto.ChatRoom;
import com.example.demo.member.Member;

@Repository
public interface ChatDao extends JpaRepository<Chat, Long>{
//	ArrayList<Chat> findByRoomNum(ChatRoom room);
}
