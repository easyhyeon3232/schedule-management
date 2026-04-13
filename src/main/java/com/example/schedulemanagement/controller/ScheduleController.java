package com.example.schedulemanagement.controller;

import com.example.schedulemanagement.dto.CreateScheduleResponseDto;
import com.example.schedulemanagement.dto.CreatescheduleRequestDto;
import com.example.schedulemanagement.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
