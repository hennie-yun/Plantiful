package com.example.demo.schedule;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ScheduleDao extends JpaRepository<Schedule, Integer> {
}
