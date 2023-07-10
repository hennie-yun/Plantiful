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
import org.springframework.web.bind.annotation.RequestParam;
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
	@PostMapping("/{email}")
	public Map<String, Object> savePayment(@PathVariable("email") String email, PaymentDto dto) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("axios 타서 왔어");
		System.out.println(dto.getEmail());
		System.out.println(dto.getPaidamount());
		
		MemberDto memDto = memservice.getMember(email);
		System.out.println("memberdto = " + memDto);
		PaymentDto oldPayment = service.findByEmail(memDto);
		
		System.out.println("oldPayment = " + oldPayment);
		
		if (oldPayment != null) {
			System.out.println("여기입니다" + dto);
			System.out.println("원래있던 금액이라죠 " + oldPayment.getPaidamount());
			
			int newPaidAmount = oldPayment.getPaidamount() + dto.getPaidamount();
			System.out.println("새 금액입니다링");
			System.out.println(newPaidAmount);
			
			
			oldPayment.setPaidamount(newPaidAmount);
			
			Payment savedPayment = service.edit(oldPayment);
			
			System.out.println("savedPayment" + savedPayment);
			map.put("dto", savedPayment);
			map.put("message", newPaidAmount + "가 충전 되었습니다");
		} else {
			PaymentDto savedPayment = service.save(dto);
			map.put("message", "포인트가 충전 되었습니다");
			map.put("dto", savedPayment);
		}

		System.out.println(dto);
		return map;
	}

	//돈빼오고 
	@PostMapping("/withdraw/{email}")
	public Map<String, Object> cashout(@PathVariable("email") String email, PaymentDto dto) {
		Map<String, Object> map = new HashMap<>();
		
		System.out.println(dto.getPaidamount());
		MemberDto memDto = memservice.getMember(email);
		
		PaymentDto oldPayment = service.findByEmail(memDto);
		System.out.println("dto>>>>>>>>>>>" + dto);
		System.out.println(oldPayment);
		
		if (oldPayment != null) {
			int newPaidAmount = oldPayment.getPaidamount() - dto.getPaidamount();
			oldPayment.setPaidamount(newPaidAmount);
			Payment savedPayment = service.edit(oldPayment);
			map.put("dto", savedPayment);
			map.put("message", "포인트가 출금 되었습니다.");
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

	    MemberDto memDto = memservice.getMember(email);
	    PaymentDto paymentdto = service.findByEmail(memDto);

	    System.out.println("payment: " + paymentdto);

	    if (paymentdto != null) {
	        map.put("paydto", paymentdto);
	    } else {
	        map.put("message", "돈이 없습니다");
	        map.put("paydto", null);
	    }

	    return map;
	}


}
