package com.example.demo.payment;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.member.Member;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {

	Payment findByEmail(Member email);

	
}
