package com.example.demo.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService service;

	//결제 하면 금액 추가 
	
//	public Map save(PaymentDto dto) {
//		Map map = new HashMap<>();
//		Payment payment = service.findByEmail(dto.getEmail());
//		if (payment == null) {
//			service.save(dto);
//		} else
//			map.put("dto", dto);
//		System.out.println(dto);
//		return map;
//
//	}
	
	@PostMapping("")
	public Map save(PaymentDto dto) {
		Map map = new HashMap<>();
	    Payment payment = service.findByEmail(dto.getEmail()); //이메일이 있나 찾아
	    

	    if (payment != null) {
	        // 동일한 이메일 있으면? 
	        int newPaidAmount = payment.getPaidamount() + dto.getPaidamount();
	        
	        dto.setPaidamount(newPaidAmount); // 결제 정보의 Paidamount를 업데이트
	        service.save(dto);
	    } else {
	    	service.save(dto);
	    }
	    map.put("dto", dto);
		System.out.println(dto);
		return map;
	    }
	
}
