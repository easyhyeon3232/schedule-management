package com.example.schedulemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreatescheduleRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200)
    private String title;
    private String content;

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 50)
    private String name;

    @Size(min = 4, max = 100)
    private String password;
}
