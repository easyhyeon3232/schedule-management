package com.example.schedulemanagement.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
// Sprig Data JPA가 제공하는 기능으로 생성 시각과 수정 시각을 알아서 관리
@EntityListeners(AuditingEntityListener.class)
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String password;

    @CreatedDate   // 작성일
    @Column(updatable = false)  // 작성일은 수정 불가능하게 설정
    private LocalDateTime createDate;

    @LastModifiedDate  // 수정일
    private LocalDateTime updateDate;

    public Schedule(String title, String content, String name, String password) {
        this.title = title;
        this.content = content;
        this.name = name;
        this.password = password;
    }

    public void update(String title, String name, String password) {
        this.title = title;
        this.name = name;
        this.password = password;
    }
}
