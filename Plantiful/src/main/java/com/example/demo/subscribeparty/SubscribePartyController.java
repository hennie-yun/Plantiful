package com.example.demo.subscribeparty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	@PostMapping("")
	public Map add(SubscribePartyDto dto) {	
		System.out.println(dto);
		//포인트 빠져나감 넣기
		SubscribePartyDto dto2 = service.save(dto);
		Map map = new HashMap();
		if(dto2 != null) {
			map.put("dto", dto2);
		}
		return map;
	}
	
	
	//파티 시작 여부 수정
	@PatchMapping("/{subscribe_num}") //컬럼 일부 수정
	public Map editStart(@PathVariable("subscribe_num") int subscribe_num) {
		boolean flag = true;
		try {
			service.editStart(subscribe_num);
		} catch (Exception e) {
			flag = false;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}
	
	
	
	//subscribe_num으로 검색 (파티 전체 검색)
	@GetMapping("/party/{subscribe_num}")
	public Map getBySubNum(@PathVariable("subscrbie_num") int subscribe_num) {
		ArrayList<SubscribePartyDto> list = service.getBySubNum(subscribe_num);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	//email 로 검색
	@PostMapping("/{email}")
	public Map getByEmail(@PathVariable("email") String email) {
		ArrayList<SubscribePartyDto> list = service.getByEmail(email);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	
	//pk로 검색
	@GetMapping("/{party}")
	public Map getDetail(@PathVariable("party") int party) {
		SubscribePartyDto dto = service.getParty(party);
		Map map = new HashMap();
		map.put("dto", dto);
		return map;
	}
	
	//email이랑 subnum으로 검색 (이미 참여했는지 아닌지 확인)
//	@GetMapping("/{subscribe_num}/{email}")
//	public Map getAddCheck(@PathVariable("subscribe_num") int subscribe_num, @PathVariable("email") String email) {
//		SubscribePartyDto dto = service.getByEmailSubnum(subscribe_num, email);
//		System.out.println(dto);
//		Map map = new HashMap();
//		map.put("dto", dto);
//		return map;
//	}
}
