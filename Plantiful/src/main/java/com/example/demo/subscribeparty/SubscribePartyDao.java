package com.example.demo.subscribeparty;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.member.Member;
import com.example.demo.subscribeboard.SubscribeBoard;

import jakarta.transaction.Transactional;

@Repository
public interface SubscribePartyDao extends JpaRepository<SubscribeParty, Integer> {

	ArrayList<SubscribeParty> findBySubscribeNum(SubscribeBoard subscribe_num);

	ArrayList<SubscribeParty> findByEmailOrderByStartcheckAsc(Member email);


	@Transactional
	@Modifying
	@Query(value = "update subscribe_party set start_check=1 where subscribe_num=:subscribe_num", nativeQuery = true)
	void updateStartCheck(@Param("subscribe_num") int subscribe_num);
	
	@Query(value = "update subscribe_party set start_check=2 where subscribe_num=:subscribe_num", nativeQuery = true)
	void endStartCheck(@Param("subscribe_num") int subscribe_num);
	
	@Query(value = "update subscribe_party set start_check=0 where subscribe_num=:subscribe_num", nativeQuery = true)
	void beforeStartCheck(@Param("subscribe_num") int subscribe_num);

	SubscribeParty findBySubscribeNumAndEmail(SubscribeBoard subscribe_num, Member email);

	//진행중인거 기간 끝난 후 모집자에게 돈 보내줌
	//돈 빼냄 -> 모집자 cash로 이동 
	@Query(value = "update subscribe_party set point_basket=0 where subscribe_num=:subscribe_num", nativeQuery = true)
	void minusPeople(@Param("subscribe_num") int subscribe_num);
	
	
}
