package com.example.demo.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentDao dao;

	
	// 처음 결제 하는 거면 추가 
		public PaymentDto save(PaymentDto dto) {
			Payment entity = dao.save(new Payment(dto.getPaidamount(),dto.getEmail(), dto.getPaidamount()));
			return new PaymentDto(entity.getPaymentnum(), entity.getEmail(),entity.getPaidamount());
		}
	
		//같은 이메일이 있으면 추가가 아니라 금액만 update 
		public Payment edit(PaymentDto oldPayment) {
			Payment e = new Payment(oldPayment.getPaymentnum(),oldPayment.getEmail(),oldPayment.getPaidamount());
			Payment entity = dao.save(e);
//			Payment entity = dao.updateByPaymentnum(oldPayment.getPaidamount(), oldPayment.getPaymentnum());
			return entity;

		}
		//이메일 String 으로 찾기 
		  @Transactional
		    public PaymentDto findByEmail(MemberDto dto) {
			  Member email = new Member(dto.getEmail(), dto.getPwd(), dto.getNickname(), dto.getPhone(), dto.getId(), dto.getImg());
		      Payment payment = dao.findByEmail(email);
		      if(payment == null) {
		    	  return null;
		      }
		      return new PaymentDto (payment.getPaymentnum(), payment.getEmail(),payment.getPaidamount());
		    }
		
}
