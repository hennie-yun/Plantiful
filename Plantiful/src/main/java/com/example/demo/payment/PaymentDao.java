package com.example.demo.payment;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.member.Member;

@Repository
public interface PaymentDao extends JpaRepository<Payment, String> {

	ArrayList<Payment> findAllByEmail(Member email);
	
}
