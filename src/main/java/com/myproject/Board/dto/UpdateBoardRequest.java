package com.myproject.Board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateBoardRequest {
    private String title;
    private String content;
    private MultipartFile boardContentImg;
}
