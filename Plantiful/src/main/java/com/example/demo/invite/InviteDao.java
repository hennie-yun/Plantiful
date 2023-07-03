package com.example.demo.invite;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.member.Member;
import com.example.demo.schedulegroup.ScheduleGroup;

@Repository
public interface InviteDao extends JpaRepository<Invite, Integer> {
	ArrayList<Invite> findByEmail(Member email);

	ArrayList<Invite> findByGroupnum(ScheduleGroup groupnum);
}
