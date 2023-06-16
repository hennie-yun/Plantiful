package com.example.demo.subscribeparty;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.subscribeboard.SubscribeBoardService;

@RestController
@Controller
@RequestMapping("/subscribeparty")
public class SubscribePartyController {

	
	@Autowired
	private SubscribePartyService service; 
	@Autowired
	private SubscribeBoardService sbservice;
	
	//추가
	//추가할때 board의 add party 값 바꿔줌
//	public Map add(SubscribePartyDto dto) {
//		boolean flag = true;
//		
//	}
	
	
	//수정
	//모집 인원만큼 다 차면
	//
	
	//subscribe_num으로 검색
	
	//email 로 검색
	
	
	//pk로 검색
}
