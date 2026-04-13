package com.example.schedulemanagement.service;

import com.example.schedulemanagement.dto.CreateScheduleResponseDto;
import com.example.schedulemanagement.dto.CreatescheduleRequestDto;
import com.example.schedulemanagement.dto.GetAllResponseDto;
import com.example.schedulemanagement.entity.Schedule;
import com.example.schedulemanagement.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    // 속성
    private final ScheduleRepository scheduleRepository;


    // 생

    // 기능
    @Transactional
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

    @Transactional(readOnly = true)
    public List<GetAllResponseDto> findAllByName(String name) {

        List<Schedule> scheduleList;

        // ASC 오름차순 (1 -> 10)
        // DESC 내림차순 (10 -> 1)
        if (name != null) {
            scheduleList = scheduleRepository.findAllByNameOrderByUpdateDateDesc(name);
        } else {
            scheduleList = scheduleRepository.findAllByOrderByUpdateDateDesc();
        }
        // 빈 바구니 준비
        List<GetAllResponseDto> dtos = new ArrayList<>();

        // DB에서 가져온 원본 리스트를 하나씩 꺼내서 확인
        for (Schedule schedule : scheduleList) {
            GetAllResponseDto dto = new GetAllResponseDto(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getName(),
                    schedule.getCreateDate(),
                    schedule.getUpdateDate()
            );

            // 포장 완료된 DTO를 바구니에 차곡차곡 쌓는다.
            dtos.add(dto);
        }
        return dtos;
    }
}

