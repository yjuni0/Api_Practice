package com.myproject.Board.dto;

import com.myproject.Board.entity.Board;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class BoardResponse {
    private String title;
    private String content;
    private String boardContentimg;
    private LocalDate createDate;

    public BoardResponse(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.boardContentimg=board.getBoardContentImg();
        this.createDate=board.getCreateDate();
    }
}
