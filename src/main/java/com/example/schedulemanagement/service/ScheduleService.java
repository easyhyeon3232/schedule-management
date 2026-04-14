package com.example.schedulemanagement.service;

import com.example.schedulemanagement.dto.*;
import com.example.schedulemanagement.entity.Schedule;
import com.example.schedulemanagement.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    
    @Transactional(readOnly = true)
    public GetOneScheduleResponseDto getOneSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );

        return new GetOneScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getName(),
                schedule.getCreateDate(),
                schedule.getUpdateDate()
        );
    }

    @Transactional
    public UpdateScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );

        // 비밀번호 검증하는 로직
        if (!(requestDto.getPassword().equals(schedule.getPassword()))) {
            // 500대신 400대 에러를 던지기 위해서
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            //throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 생각해보자 업데이트를 하려면 우선 일정 제목과 작성자명만 수정가능해 그러면 요청을 가져와지
        // 수정할 데이터를 db에 업데이트해줘야 해
        // 요청안에는 사용자가 보낸 새 데이터가 있음ㅁ
        schedule.update(
                requestDto.getTitle(),    // requeest에서 새 데이터를 집어넣음
                requestDto.getName(),
                requestDto.getPassword()
        );

        // 확인
        UpdateScheduleResponseDto updateScheduleResponseDto = new UpdateScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getName(),
                schedule.getCreateDate(),    // 작성일 변경되는지 확인
                schedule.getUpdateDate()
        );
        return updateScheduleResponseDto;
    }

    @Transactional
    public void deleteSchedule(Long id) {
        boolean existsById = scheduleRepository.existsById(id);
        if (!existsById) {
            throw new IllegalStateException("없는 일정입니다.");
        }
        scheduleRepository.deleteById(id);
    }


}

