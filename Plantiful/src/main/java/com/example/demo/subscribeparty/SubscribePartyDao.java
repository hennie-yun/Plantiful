package com.example.demo.subscribeparty;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribePartyDao extends JpaRepository<SubscribeParty, Integer> {

	//subscribe_num으로 검색
	ArrayList<SubscribeParty> findbySubNum(int subscribe_num);
	
	//email로 검색
	ArrayList<SubscribeParty> findbyEmail(SubscribeParty email);
}
