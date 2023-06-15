package com.example.demo.subscribeboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeBoardDao extends JpaRepository<SubscribeBoard, Integer>{
	
}
