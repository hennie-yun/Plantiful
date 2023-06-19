package com.example.demo.listener;

import java.util.Date;

import com.example.demo.subscribeboard.SubscribeBoard;

import jakarta.persistence.PrePersist;

public class subscribeboardlistener {
    @PrePersist
    public void prePersist(SubscribeBoard entity) {
        entity.setRegister_date(new Date()); // 현재 날짜와 시간을 설정합니다.
    }
}