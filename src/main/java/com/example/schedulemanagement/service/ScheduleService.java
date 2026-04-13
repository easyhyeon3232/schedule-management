package com.example.schedulemanagement.service;

import com.example.schedulemanagement.dto.CreateScheduleResponseDto;
import com.example.schedulemanagement.dto.CreatescheduleRequestDto;
import com.example.schedulemanagement.entity.Schedule;
import com.example.schedulemanagement.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    // 속성
    private final ScheduleRepository scheduleRepository;



    // 생

    // 기능
    public CreateScheduleResponseDto save(CreatescheduleRequestDto requestDto) {
        // 나중에 requestDto.getTitle를 엔티티 생성자에 추가해서 해보기
        Schedule schedule = new Schedule(requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getName(),
                requestDto.getPassword());

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponseDto(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getName(),
                savedSchedule.getCreateDate(),
                savedSchedule.getUpdateDate()
        );
    }
}
