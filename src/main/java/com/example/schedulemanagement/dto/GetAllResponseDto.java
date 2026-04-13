package com.example.schedulemanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetAllResponseDto {

    private Long id;
    // 서버 입구(DTO)에서 미리 걸러냄
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200)
    private String title;
    private String content;

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 50)
    private String name;

    // JSON으로 데이터를 응답할 때 날짜를 특정 형식(2026-04-14 10:00)으로 보냄
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateDate;

    public GetAllResponseDto(Long id, String title, String content, String name, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.name = name;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}