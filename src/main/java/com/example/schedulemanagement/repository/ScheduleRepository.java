package com.example.schedulemanagement.repository;

import com.example.schedulemanagement.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 이름 조건이 있을 때 사용할 메서드
    List<Schedule> findAllByNameOrderByUpdateDateDesc(String name);

    List<Schedule> findAllByOrderByUpdateDateDesc();
}
