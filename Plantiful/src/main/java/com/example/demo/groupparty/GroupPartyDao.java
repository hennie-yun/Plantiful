package com.example.demo.groupparty;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.member.Member;
import com.example.demo.schedulegroup.ScheduleGroup;

@Repository
public interface GroupPartyDao extends JpaRepository<GroupParty, Integer> {
    ArrayList<GroupParty> findByScheduleGroupNum(ScheduleGroup schedulegroup_num);
    
    ArrayList<GroupParty> findBymemberEmail(Member email);
}
