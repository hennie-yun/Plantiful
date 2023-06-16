package com.example.demo.chat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.chat.dto.Chat;

public interface ChatDao extends JpaRepository<Chat, Long> {

}
