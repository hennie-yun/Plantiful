package com.example.demo.subscribeparty;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.member.Member;
import com.example.demo.subscribeboard.SubscribeBoard;

@Service
public class SubscribePartyService {

	@Autowired
	private SubscribePartyDao dao;

//	//email이랑 subnum으로 검색 (이미 참여했는지 아닌지 확인)
//		public SubscribePartyDto getByEmailSubnum(int subscribe_num, String email) {
//			SubscribeBoard subnum = new SubscribeBoard(subscribe_num,null,null,null,0,0,null,null,null,null,null);
//			Member memail = new Member(email,null,null,null,0,null);
//			SubscribeParty dto = dao.findBySubscribeNumAndEmail(subnum, memail);
//			SubscribePartyDto dto2 = new SubscribePartyDto();
//			return new SubscribePartyDto(dto2.getParty(),dto2.getSubscribe_num(),dto2.getEmail(),dto2.getPoint_basket(),dto2.getEnddate(),dto2.getStart_check(),dto2.getSchedule_num());
//		}
	
	//추가
	public SubscribePartyDto save(SubscribePartyDto dto) {
		SubscribeParty checkdto = dao.findBySubscribeNumAndEmail(dto.getSubscribe_num(), dto.getEmail());
		if (checkdto == null) {
			SubscribeParty sp = dao.save(new SubscribeParty(dto.getParty(),dto.getSubscribe_num(),dto.getEmail(), dto.getPoint_basket(),dto.getEnddate(),dto.getStart_check(),dto.getSchedule_num()));
			return new SubscribePartyDto(sp.getParty(),sp.getSubscribeNum(),sp.getEmail(), sp.getPoint_basket(),sp.getEnddate(),sp.getStart_check(),sp.getSchedule_num());
			
		}
		return null;
	}
	
	//subscribe_num으로 검색 (파티에 몇명있는지 조회)
	public ArrayList<SubscribePartyDto> getBySubNum(int subscribe_num){ 
		SubscribeBoard subscribe_num2 = new SubscribeBoard(subscribe_num,null,"","",0,0,null,null,null,null,null);
		ArrayList<SubscribeParty> list = (ArrayList<SubscribeParty>) dao.findBySubscribeNum(subscribe_num2);
		ArrayList<SubscribePartyDto> list2 = new ArrayList<SubscribePartyDto>();
		for (SubscribeParty sp : list) {
			list2.add(new SubscribePartyDto(sp.getParty(),sp.getSubscribeNum(),sp.getEmail(), sp.getPoint_basket(),sp.getEnddate(),sp.getStart_check(),sp.getSchedule_num()));
		}
		return list2;
	}
	
//	//email로 검색
	public ArrayList<SubscribePartyDto> getByEmail(String email){
		Member email2 = new Member(email, "","","",0,"");
		ArrayList<SubscribeParty> list = (ArrayList<SubscribeParty>) dao.findByEmail(email2);
		ArrayList<SubscribePartyDto> list2 = new ArrayList<SubscribePartyDto>();
		for (SubscribeParty sp : list) {
			list2.add(new SubscribePartyDto(sp.getParty(),sp.getSubscribeNum(),sp.getEmail(), sp.getPoint_basket(),sp.getEnddate(),sp.getStart_check(),sp.getSchedule_num()));
		}
		return list2;
	}
	
	//pk로 검색
	public SubscribePartyDto getParty(int party) {
		SubscribeParty sp = dao.findById(party).orElse(null);
		if(sp == null) {
			return null;
		}
		return new SubscribePartyDto(sp.getParty(),sp.getSubscribeNum(),sp.getEmail(), sp.getPoint_basket(),sp.getEnddate(),sp.getStart_check(),sp.getSchedule_num());
	}
	
	//start check 수정
	public void editStart(int subscribe_num) {
		dao.updateStartCheck(subscribe_num);
	}
	
	//start check 끝내기
	public void endStart(int subscribe_num) {
		dao.endStartCheck(subscribe_num);
	}
	
	//돈 움직이기(팀원들 pb 0으로 만듦)
	public void minusMoney(int subscribe_num) {
		dao.minusPeople(subscribe_num);
	}
	
}
