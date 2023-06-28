package com.example.demo.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService service;

	@Autowired
	private MemberService memservice;

	// 추가
	@PostMapping("")
	public Map<String, Object> savePayment(@PathVariable("email") String email, PaymentDto dto) {
		Map<String, Object> map = new HashMap<>();
		PaymentDto oldPayment = service.findByEmail(email);
		System.out.println(oldPayment);
		if (oldPayment != null) {
			int newPaidAmount = oldPayment.getPaidamount() + dto.getPaidamount();
			System.out.println(newPaidAmount);
			oldPayment.setPaidamount(newPaidAmount);
			Payment savedPayment = service.edit(oldPayment);
			map.put("dto", savedPayment);
			map.put("message", "기존에 업데이트 완료");
		} else {
			PaymentDto savedPayment = service.save(dto);
			map.put("message", "새롭게 추가 완료");
			map.put("dto", savedPayment);
		}

		System.out.println(dto);
		return map;
	}

	//돈빼오고 
	@PostMapping("/withdraw/{email}")
	public Map<String, Object> cashout(@PathVariable("email") String email, PaymentDto dto) {
		Map<String, Object> map = new HashMap<>();

		PaymentDto oldPayment = service.findByEmail(email);

		if (oldPayment != null) {
			int newPaidAmount = oldPayment.getPaidamount() - dto.getPaidamount();
			oldPayment.setPaidamount(newPaidAmount);
			Payment savedPayment = service.edit(oldPayment);
			map.put("dto", savedPayment);
			map.put("message", "돈 잘 빠져나갔다링.");
		} else {
			map.put("message", "찾는 정보가 없습니다.");
		}
		return map;
	}

	// 돈 얼마 있나 뺴오기
	@GetMapping("/getcash/{email}")
	public Map<String, Object> getPaymentDtoByEmail(@PathVariable("email") String email) {
		System.out.println("email: " + email);
		Map<String, Object> map = new HashMap<>();
		
		PaymentDto paymentdto = service.findByEmail(email);

		System.out.println("payment: " + paymentdto);

		if (paymentdto != null) {
			map.put("paydto", paymentdto);
		} else {
			map.put("message", "찾는 정보가 없습니다");
		}

		return map;
	}

}
