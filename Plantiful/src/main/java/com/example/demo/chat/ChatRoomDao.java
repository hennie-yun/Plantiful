package com.example.demo.chat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.member.Member;
import com.example.demo.subscribeboard.SubscribeBoard;

@Repository
public interface ChatRoomDao extends JpaRepository<ChatRoom, Long>{
	
	@Query(value = "select * from chat_room where num in (select room_num from chat where email = :email)", nativeQuery = true)
	public List<ChatRoom> findByMember(@Param("email") String email);
	
	public ChatRoom findBySubscribeNum(SubscribeBoard board);
}
