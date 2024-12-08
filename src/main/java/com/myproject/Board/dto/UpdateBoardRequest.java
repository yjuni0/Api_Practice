package com.myproject.Board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateBoardRequest {
    private String title;
    private String content;
    private String boardContentImg;
}
