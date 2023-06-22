package com.example.demo.kakaopay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoReadyResponse {

    private String tid; // 결제 고유 번호
    private String next_redirect_pc_url; // pc 웹 결제 페이지
    private String created_at;
}
