package com.example.demo.subscribeparty;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribePartyService {

	@Autowired
	private SubscribePartyDao dao;

	//추가
	public void save(SubscribePartyDto dto) {
		SubscribeParty sp = dao.save(new SubscribeParty(dto.getParty(),dto.getSubscribe_num(),dto.getEmail(), dto.getPoint_basket(),dto.getRemain_month(),dto.getStart_check(),dto.getSchedule_num()));
	}
	
	//subscribe_num으로 검색 (파티에 몇명있는지 조회)
	public ArrayList<SubscribePartyDto> getBySubNum(int subscribe_num){
		ArrayList<SubscribeParty> list = (ArrayList<SubscribeParty>) dao.findbySubNum(subscribe_num);
		ArrayList<SubscribePartyDto> list2 = new ArrayList<SubscribePartyDto>();
		for (SubscribeParty sp : list) {
			list2.add(new SubscribePartyDto(sp.getParty(),sp.getSubscribe_num(),sp.getEmail(), sp.getPoint_basket(),sp.getRemain_month(),sp.getStart_check(),sp.getSchedule_num()));
		}
		return list2;
	}
	
//	//email로 검색
//	public ArrayList<SubscribePartyDto> getByEmail(String email){
//		SubscribeParty s = new SubscribeParty(0,0,Mem,0,null,0,0);
//		ArrayList<SubscribeParty> list = (ArrayList<SubscribeParty>) dao.findbyEmail(s);
//		ArrayList<SubscribePartyDto> list2 = new ArrayList<SubscribePartyDto>();
//		for (SubscribeParty sp : list) {
//			list2.add(new SubscribePartyDto(sp.getParty(),sp.getSubscribe_num(),sp.getEmail(), sp.getPoint_basket(),sp.getRemain_month(),sp.getStart_check(),sp.getSchedule_num()));
//		}
//		return list2;
//	}
	
	//pk로 검색
	public SubscribePartyDto getParty(int party) {
		SubscribeParty sp = dao.findById(party).orElse(null);
		if(sp == null) {
			return null;
		}
		return new SubscribePartyDto(sp.getParty(),sp.getSubscribe_num(),sp.getEmail(), sp.getPoint_basket(),sp.getRemain_month(),sp.getStart_check(),sp.getSchedule_num());
	}
	
}
