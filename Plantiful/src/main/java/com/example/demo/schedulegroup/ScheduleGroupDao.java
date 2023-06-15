package com.example.demo.schedulegroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ScheduleGroupDao extends JpaRepository<ScheduleGroup, Integer> {

}
