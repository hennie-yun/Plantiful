package com.example.demo.subscribeboard;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribeBoardService {

	@Autowired
	private SubscribeBoardDao dao;

	// 글 추가
	public int save(SubscribeBoardDto dto) {
		SubscribeBoard sb = dao.save(
				new SubscribeBoard(dto.getSubscribe_num(), dto.getEmail(), dto.getTitle(), dto.getSite(),
				dto.getTotal_point(), dto.getTotal_people(), dto.getRegister_date(), dto.getRecruit_endperiod(),
				dto.getPayment_date(), dto.getSubscribe_startdate(), dto.getSubscribe_enddate(), 0));

		return sb.getSubscribe_num(); //바로 상세페이지로 이동
	}

	// 글 삭제
	public void delBoard(int subscribe_num) {
		dao.deleteById(subscribe_num);
	}

	// 글 전체 목록 불러오기
	public ArrayList<SubscribeBoardDto> getAll(){
		ArrayList<SubscribeBoard> list = (ArrayList<SubscribeBoard>) dao.findAll();
		ArrayList<SubscribeBoardDto> list2 = new ArrayList<SubscribeBoardDto>();
		for (SubscribeBoard sb : list) {
			list2.add(new SubscribeBoardDto(sb.getSubscribe_num(), sb.getEmail(), sb.getTitle(),sb.getSite(),sb.getTotal_point(),sb.getTotal_people(),sb.getRegister_date(),sb.getRecruit_endperiod(),sb.getPayment_date(),sb.getSubscribe_startdate(),sb.getSubscribe_enddate(),sb.getAdd_check()));
		}
		return list2;
	}

	// 글 상세정보 불러오기 (subscribe_num)
	// 여기 파라메터 넘버는 링크나 그런거에서 건너올때의 이름 말하는거인가?
	public SubscribeBoardDto getBoard(int subscribe_num) {
		SubscribeBoard sb = dao.findById(subscribe_num).orElse(null);
		if(sb == null) {
			return null;
		}
		return new SubscribeBoardDto(sb.getSubscribe_num(), sb.getEmail(), sb.getTitle(),sb.getSite(),sb.getTotal_point(),sb.getTotal_people(),sb.getRegister_date(),sb.getRecruit_endperiod(),sb.getPayment_date(),sb.getSubscribe_startdate(),sb.getSubscribe_enddate(),sb.getAdd_check());
	}
}
