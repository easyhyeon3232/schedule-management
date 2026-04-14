package com.example.schedulemanagement.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponseDto {
    private Long id;
    private String title;
    private String content;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public UpdateScheduleResponseDto(Long id, String title, String content, String name, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.name = name;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
