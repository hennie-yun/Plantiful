package com.example.demo.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.member.Member;

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
			Payment entity = dao.save(new Payment(oldPayment.getPaymentnum(),oldPayment.getEmail(),oldPayment.getPaidamount()));
			return entity;

		}
	
		
		
		//이메일 String 으로 찾기 
		  @Transactional
		    public PaymentDto findByEmail(String email) {
		      Payment payment = dao.findByEmail(email);
		      return new PaymentDto (payment.getPaymentnum(), payment.getEmail(),payment.getPaidamount());
		    }
		
}
