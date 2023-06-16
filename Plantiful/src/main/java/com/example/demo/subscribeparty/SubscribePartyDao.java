package com.example.demo.subscribeparty;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.member.Member;
import com.example.demo.subscribeboard.SubscribeBoard;

@Repository
public interface SubscribePartyDao extends JpaRepository<SubscribeParty, Integer> {

    ArrayList<SubscribeParty> findBySubscribeNum(SubscribeBoard subscribe_num);
	
    ArrayList<SubscribeParty> findByEmail(Member email);
}

