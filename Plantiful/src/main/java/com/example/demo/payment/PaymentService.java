package com.example.demo.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.member.Member;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentDao dao;
	
	//페이 결과로 찾기 
	public PaymentDto paymentLookupService(int paymentnum) {
		Payment payment = dao.findById(paymentnum).orElse(null);
		if (payment == null) {
			return null;
		}
		return new PaymentDto(payment.getPaymentnum(), payment.getImpuid(), payment.getMerchantuid(), payment.getPaidamount(),
				payment.getApplynum(), payment.getEmail());
	}
	
	// 결제 완료 하면  추가 
		public PaymentDto save(PaymentDto dto) {
			Payment entity = dao.save(new Payment(dto.getPaymentnum(), dto.getImpuid(), dto.getMerchantuid(), dto.getPaidamount(),
					dto.getApplynum(), dto.getEmail()));
			return new PaymentDto(entity.getPaymentnum(), entity.getImpuid(), entity.getMerchantuid(), entity.getPaidamount(), entity.getApplynum(),
					entity.getEmail());
		}
		
		//결제 완료 했는데 같은 이메일이 있을 시 Paidamount를 더해 
//		public String update(PaymentDto dto) {
//			
//		}

		//회원의 돈 찾기 
		public Payment findByEmail(Member email) {
	        return dao.findByEmail(email);
	    }
		
		//삭제하기
		public void withdraw(int paymentnum) {
			dao.deleteById(paymentnum);
		}

}
