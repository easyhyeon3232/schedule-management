package com.example.schedulemanagement.controller;

import com.example.schedulemanagement.dto.*;
import com.example.schedulemanagement.entity.Schedule;
import com.example.schedulemanagement.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    // 속
    private final ScheduleService scheduleService;


    // 생
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 기
    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> create(@RequestBody CreatescheduleRequestDto requestDto) {
        CreateScheduleResponseDto responseDto = scheduleService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    // 작성자명은 있을 수도 있고, 없을 수도 있다.
    public ResponseEntity<List<GetAllResponseDto>> getAll(@RequestParam(required = false) String name) {

        // 작성자명이 파라미터로 들어온 경우 -> 이름으로 필터링해서 조히
        List<GetAllResponseDto> getAllResponseDto = scheduleService.findAllByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(getAllResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetOneScheduleResponseDto> getOne(@PathVariable Long id) {
        GetOneScheduleResponseDto getOneScheduleResponseDto = scheduleService.getOneSchedule(id);
        return ResponseEntity.status(HttpStatus.OK).body(getOneScheduleResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateScheduleResponseDto> update(@PathVariable Long id, @RequestBody UpdateScheduleRequestDto requestDto) {
        UpdateScheduleResponseDto updateScheduleResponseDto = scheduleService.updateSchedule(id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateScheduleResponseDto);
    }


    // 업데이트 부터 하고 나머지 작성
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }






}
