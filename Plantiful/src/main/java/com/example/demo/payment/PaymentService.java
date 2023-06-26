package com.example.demo.payment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.example.demo.member.Member;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.siot.IamportRestClient.response.IamportResponse;

@Service
public class PaymentService {

	@Autowired
	private PaymentDao dao;
	
	

	// 결제 고유ID로 찾기, 확인 -> 결과 값이 있나 없나로 환불 진행
	public PaymentDto paymentLookupService(String imp_uid) {
		Payment payment = dao.findById(imp_uid).orElse(null);
		if (payment == null) {
			return null;
		}
		return new PaymentDto(payment.getImpuid(), payment.getMerchantuid(), payment.getPaidamount(),
				payment.getApplynum(), payment.getEmail());
	}

	// 추가
	public PaymentDto save(PaymentDto dto) {
		Payment entity = dao.save(new Payment(dto.getImpuid(), dto.getMerchantuid(), dto.getPaidamount(),
				dto.getApplynum(), dto.getEmail()));
		return new PaymentDto(entity.getImpuid(), entity.getMerchantuid(), entity.getPaidamount(), entity.getApplynum(),
				entity.getEmail());
	}

	// 결제 인증
//	public void verifyIamportService(IamportResponse<Payment> irsp, int amount) 
//			throws verifyIamportException {
//	
//		
//		//실제로 결제된 금액과 아임포트 서버쪽 결제내역 금액과 같은지 확인
//		//이때 가격은 BigDecimal이란 데이터 타입으로 주로 금융쪽에서 정확한 값표현을 위해씀.
//		//int형으로 비교해주기 위해 형변환 필요.
//		if(irsp.getResponse().getAmount().intValue()!=amount)
//			throw new verifyIamportException();
//				
//		//DB에서 물건가격과 실제 결제금액이 일치하는지 확인하기. 만약 다르면 예외 발생시키기.
//		if(amount!=actionBoard.getImmediatly()) 
//			throw new verifyIamportException();
//		
//		//아임포트에서 서버쪽 결제내역과 DB의 결제 내역 금액이 같으면 DB에 결제 정보를 삽입.
//		Member member = memberService.findByEmail(irsp.getResponse().getBuyerEmail());
//		
//		PaymentsInfo paymentsInfo = PaymentsInfo.builder()
//				.payMethod(irsp.getResponse().getPayMethod())
//				.impUid(irsp.getResponse().getImpUid())
//				.merchantUid(irsp.getResponse().getMerchantUid())
//				.amount(irsp.getResponse().getAmount().intValue())
//				.buyerAddr(irsp.getResponse().getBuyerAddr())
//				.buyerPostcode(irsp.getResponse().getBuyerPostcode())
//				.member(member)
//				.actionBoard(actionBoard)
//				.build();
//		
//		paymentRepository.save(paymentsInfo);
//	}

	// 멤버기준 찾기

	// 환불 -> 이걸 금액을 0으로 바꾸면서 환불 얼마나 한지 확인 하게 해야할듯 고로 update
//
//	public CancelData cancelData(Map<String, String> map, IamportResponse<Payment> lookUp, String code)
//			throws RefundAmountIsDifferent {
//		// 아임포트 서버에서 조회된 결제금액 != 환불(취소)될 금액 이면 예외발생
//
//		if (lookUp.getResponse().getAmount() != new BigDecimal(map.get("checksum")))
//			throw new RefundAmountIsDifferent();
//
//		CancelData data = new CancelData(lookUp.getResponse().getImpUid(), true);
//		data.setReason(map.get("reason"));
//		data.setChecksum(new BigDecimal(map.get("checksum")));
//		data.setRefund_holder(map.get("refundHolder"));
//		data.setRefund_bank(code);
//		return data;
//	}
	
	
	//토큰 받아 
//	public String getToken() throws IOException {
//		HttpsURLConnection conn = null;
//		URL url = new URL("https://api.iamport.kr/users/getToken");
//		conn = (HttpsURLConnection) url.openConnection();
//		conn.setRequestMethod("POST");
//		conn.setRequestProperty("Content-type", "application/json");
//		conn.setRequestProperty("Accept", "application/json");
//		conn.setDoOutput(true);
//		JsonObject json = new JsonObject();
//		json.addProperty("imp_key", imp_key);
//		json.addProperty("imp_secret", imp_secret);
//		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//		bw.write(json.toString());
//		bw.flush();
//		bw.close();
//		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//		Gson gson = new Gson();
//		String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();
//		System.out.println("response : " + response);
//		String token = gson.fromJson(response, Map.class).get("access_token").toString();
//		br.close();
//		conn.disconnect();
//		return token;
//	}

}
