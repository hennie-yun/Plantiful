package com.example.demo.kakaoaccesstoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface KakaotokenDao extends JpaRepository<Kakaotoken, Integer> {

	@Transactional
	@Query(value = "select * from kakaotoken where email = :email", nativeQuery = true)
	Kakaotoken findByEmail(@Param("email") String eamil);
}
