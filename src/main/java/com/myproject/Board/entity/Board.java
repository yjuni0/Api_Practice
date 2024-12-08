package com.myproject.Board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;


import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id //id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키에 자동 증가 값 지정
    @Column (name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "boardContentImg", nullable = false)
    private String boardContentImg;

    @Column(name = "createDate", nullable = false, updatable = false)
    @CreatedDate
    private LocalDate createDate;

    @Builder
    public Board(String title, String content, String boardContentImg) {
        this.title = title;
        this.content = content;
        this.boardContentImg = boardContentImg; // 기본 이미지 처리
        this.createDate = LocalDate.now();
    }

}
