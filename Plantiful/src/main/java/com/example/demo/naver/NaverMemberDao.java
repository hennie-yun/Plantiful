package com.example.demo.naver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface NaverMemberDao extends JpaRepository<Navertoken, Integer> {
	
	@Transactional
	@Query(value = "select * from navertoken where email = :email", nativeQuery = true)
	
	Navertoken findByEmail(@Param("email") String email);
}
