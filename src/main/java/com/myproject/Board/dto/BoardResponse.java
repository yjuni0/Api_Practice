package com.myproject.Board.dto;

import com.myproject.Board.entity.Board;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BoardResponse {
    private final String title;
    private final String content;
    private final String boardContentImg;
    private final LocalDate createDate;

    public BoardResponse(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.boardContentImg = board.getBoardContentImg();
        this.createDate = board.getCreateDate();
    }
}
