package com.example.demo.payment;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.member.Member;

import jakarta.transaction.Transactional;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {

	@Transactional
	@Query(value = "select * from payment where email = :email", nativeQuery = true)
	Payment findByEmail(@Param("email") String eamil);
}
