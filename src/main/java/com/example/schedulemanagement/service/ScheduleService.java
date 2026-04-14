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

/**
 * 일정 관리 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * 일정 생성, 조회, 수정, 삭제 (CRUD) 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
public class ScheduleService {
    // 속성
    private final ScheduleRepository scheduleRepository;

    

    // 생

    // 기능

    /**
     * 새로운 일정을 저장합니다.
     * @param requestDto 일정 생성을 위한 데이터(제목, 내용,, 작성자명, 비밀번호)
     * @return 생성된 일정의 데이터가 담긴 DTO
     */
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

    /**
     * 전체 일정 목록을 조회합니다. 이름에 제공될 경우 해당 작성자의 일정만 필터링합니다.
     * 결과는 수정일 기준 내림차순으로 정렬됩니다.
     * @param name 필터링할 작성자명 (null일 경우 전체 조회)
     * @return 일정 정보 목록(DTO 리스트)
     */
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

    /**
     * 특정 식별자(id)에 해당하는 단일 일정을 조회합니다.
     * @param id 조회할 일정의 고유 ID
     * @return 조회된 일정 생세 정보 DTO
     * @throws IllegalStateException 해당 ID의 일정이 존재하지 않을 경우 발생
     */
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

    /**
     * 깆존 일정 정보를 수정합니다. 수정 시 비밀번호 일치 여부를 확인합니다.
     * @param id 수정할 일정의 고유 ID
     * @param requestDto 수정할 데이터(제목, 작성자명, 비밀번호 등)
     * @return 수정이 완료된 일정 정보 DTO
     * @throws IllegalStateException 해당 ID의 일정이 존재하지 않을 경우 발생
     * @throws ResponseStatusException 비밀번호가 일치하지 않을 경우 (400 Bad Request))
     */
    @Transactional
    public UpdateScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );

        // 비밀번호 검증
        if (!(requestDto.getPassword().equals(schedule.getPassword()))) {
            // 500대신 400대 에러를 던지기 위해서
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
            //throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 엔티티 업데이트
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

    /**
     * 특정 일정을 삭제합니다. 삭제 시 비밀번호 검증이 필요합니다.
     * @param id 삭제할 일정의 고유 ID
     * @param requestDto 삭제 확인을 위한 비밀번호를 포함한 DTO
     * @throws IllegalStateException 해당 ID의 일정이 존재하지 않을 경우 발생
     * - 비밀번호가 일치하지 않는 경우 (400 BAD_REQUEST)
     */
    @Transactional
    public void deleteSchedule(Long id, DeleteSchduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );
        if (!(requestDto.getPassword().equals(schedule.getPassword()))) {
            // 500대신 400대 에러를 던지기 위해서
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
        scheduleRepository.deleteById(id);
    }
}

