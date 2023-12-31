package com.example.demo.subscribeboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.subscribeparty.SubscribePartyService;

@RestController
@Controller
@RequestMapping("/subscribeboard")
public class SubscribeBoardController {

	@Autowired
	private SubscribeBoardService service;
	@Autowired
	private SubscribePartyService spservice; 
	//추가
	@PostMapping("")
	public Map add(SubscribeBoardDto dto) {
//		dto.makeDate();
//		System.out.println(dto.getPayment_date());
		Map map = new HashMap();
		SubscribeBoardDto dto2 = null;
		boolean flag = true;
		try {
			int subscribe_num = service.save(dto);
			dto2 = service.getBoard(subscribe_num);
		} catch(Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		map.put("dto2", dto2);
		System.out.println(dto2);
		return map;
	}
	
	//글 삭제 기능
	@DeleteMapping("/{subscribe_num}")
	public Map delete(@PathVariable("subscribe_num") int subscribe_num) {
		Map map = new HashMap();
		boolean flag = true;
		try {
			service.delBoard(subscribe_num);
		} catch (Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		return map;
	}
	
	//전체 목록 검색
	@GetMapping("")
	public Map getAll() {
		ArrayList<SubscribeBoardDto> list = service.getAll();
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	//상세 정보 검색
	@GetMapping("/{subscribe_num}")
	public Map getByNum (@PathVariable("subscribe_num") int subscribe_num) {
		SubscribeBoardDto dto = service.getBoard(subscribe_num);
		Map map = new HashMap();
		map.put("dto", dto);
		return map;
	}
	
	//사이트별로 검색
	@GetMapping("/site/{site}") //주소 조심하기 !! 
	public Map getBySite (@PathVariable("site") String site) {
		ArrayList<SubscribeBoardDto> list = service.getBySite(site);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
}
