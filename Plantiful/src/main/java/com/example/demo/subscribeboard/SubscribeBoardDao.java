package com.example.demo.subscribeboard;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeBoardDao extends JpaRepository<SubscribeBoard, Integer>{
	//사이트 별로 검색
	ArrayList<SubscribeBoard> findBySiteOrderBySubscribeNumAsc(String site);
}
