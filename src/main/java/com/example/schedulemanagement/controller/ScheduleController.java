package com.example.schedulemanagement.controller;

import com.example.schedulemanagement.dto.*;
import com.example.schedulemanagement.entity.Schedule;
import com.example.schedulemanagement.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 일정 관리 관련 HTTP 요청을 처리하는 컨트롤러입니다.
 * 모든 요청은 "/schedules" 경로를 기본으로 합니다.
 */
@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    // 속
    private final ScheduleService scheduleService;

    /**
     * ScheduleController 생성자입니다.
     * @param scheduleService 일정 관리 비즈니스 로직을 처리하는 서비스
     */
    // 생
    // 이렇게 쓰는 대신 @RequiredArgsConstructor을 써도 된다.
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 기

    /**
     * 새로운 일정을 생성합니다.
     * @param requestDto 생성할 일정의 정보를 담은 DTO
     * @return 생성된 일정 정보와 함께 201 Created 상태 코드를 반환
     */
    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> create(@RequestBody CreatescheduleRequestDto requestDto) {
        CreateScheduleResponseDto responseDto = scheduleService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /**
     * 전체 일정 목록을 조회합니다.
     * 작성자명(name) 쿼리 파라미터가 있을 경우 해당 작성자의 일정만 필터링하여 조회합니다.
     * @param name 필터링할 작성자 이름 (선택 사항)
     * @return 조회된 일정 목록 리스트와 함께 200 ok 상태 코드를 반환
     */
    @GetMapping
    // 작성자명은 있을 수도 있고, 없을 수도 있다.
    public ResponseEntity<List<GetAllResponseDto>> getAll(@RequestParam(required = false) String name) {

        // 작성자명이 파라미터로 들어온 경우 -> 이름으로 필터링해서 조히
        List<GetAllResponseDto> getAllResponseDto = scheduleService.findAllByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(getAllResponseDto);
    }

    /**
     * 특정 식별자(ID)를 가진 일정을 상세 조회합니다.
     * @param id 조회할 일정의 고유의 식별자(ID)
     * @return 조회된 일정 상세 정보와 함께 200 ok 상태 코드를 반화나
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetOneScheduleResponseDto> getOne(@PathVariable Long id) {
        GetOneScheduleResponseDto getOneScheduleResponseDto = scheduleService.getOneSchedule(id);
        return ResponseEntity.status(HttpStatus.OK).body(getOneScheduleResponseDto);
    }

    /**
     * 특정 일정의 정보를 부분 수정합니다.
     * @param id 수정할 일정의 고유 식별자(ID)
     * @param requestDto 수정할 정보를 담은 DTO (비밀번호 검증도 포함)
     * @return 수정 완료된 일정 정보와 함께 200 ok 상태 코드를 반환
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UpdateScheduleResponseDto> update(@PathVariable Long id, @RequestBody UpdateScheduleRequestDto requestDto) {
        UpdateScheduleResponseDto updateScheduleResponseDto = scheduleService.updateSchedule(id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateScheduleResponseDto);
    }


    /**
     * 특정 일정을 삭제합니다.
     * @param id 삭제할 일정의 고유 식별자(ID)
     * @param requestDto 삭제 확인을 위한 비밀번호를 담은 DTO
     * @return 성공 시 204 No Content 상태 코드를 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestBody DeleteSchduleRequestDto requestDto) {
        scheduleService.deleteSchedule(id, requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
