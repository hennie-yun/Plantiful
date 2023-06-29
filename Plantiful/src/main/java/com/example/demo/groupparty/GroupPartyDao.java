package com.example.demo.groupparty;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.member.Member;
import com.example.demo.schedulegroup.ScheduleGroup;

@Repository
public interface GroupPartyDao extends JpaRepository<GroupParty, Integer> {

	ArrayList<GroupParty> findByschedulegroupnum(ScheduleGroup schedulegroupnum);

	ArrayList<GroupParty> findBymemberEmail(Member email);

	@Query("SELECT gp.schedulegroupnum.schedulegroup_title FROM GroupParty gp WHERE gp.memberEmail = :member AND gp.schedulegroupnum = :scheduleGroupNum")
	String findScheduleGroupTitleByMemberAndScheduleGroupNum(@Param("member") Member member, @Param("scheduleGroupNum") ScheduleGroup scheduleGroupNum);



}
