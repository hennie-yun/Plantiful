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

	
//	
//	private IamportClient iamportClient;

	@Autowired
	private PaymentService service;
//
//	
//	@Autowired
//	private final IamportClient iamportClientApi;
//
//	
//	IamportClient client = new IamportClient("6828054376104647", "0kYhkRiK36tiyg4YtBaERfyhA7TcnJx496IEGOE44gQMXSeT7NjTgRnc5pYHEQVl4CXmOpGwSRbNiZLC", true);
//
//	public IamportResponse<Payment> paymentLookup(String impUid) throws IamportResponseException, IOException{
//		return iamportClientApi.paymentByImpUid(impUid);
//	}
//	
//	@ResponseBody
//	@PostMapping("/verify/{imp_uid}")
//	public IamportResponse<Payment> paymentByImpUid(@PathVariable("imp_uid") String imp_uid, PaymentDto dto)
//			throws IamportResponseException, IOException {
//		return iamportClient.paymentByImpUid(imp_uid);
//	}
	
	@PostMapping("")
	public Map save (PaymentDto dto) {
		Map map = new HashMap<>();
		service.save(dto);
		map.put("dto", dto);
		System.out.println(dto);
		return map;
		
	}
			
	
}
