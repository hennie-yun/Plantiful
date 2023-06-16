package com.example.demo.groupparty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPartyDao extends JpaRepository<GroupParty, Integer> {
	
}
