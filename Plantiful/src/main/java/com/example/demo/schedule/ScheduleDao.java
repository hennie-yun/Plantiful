package com.example.demo.schedule;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.member.Member;
import com.example.demo.schedulegroup.ScheduleGroup;

@Repository
public interface ScheduleDao extends JpaRepository<Schedule, Integer> {
	ArrayList<Schedule> findByemail(Member email);
	ArrayList<Schedule> findBygroupnum (ScheduleGroup group_num);
}
