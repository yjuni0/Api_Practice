package com.myproject.Board.dto;

import com.myproject.Board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddBoardRequest {
    private String title;
    private String content;
    private MultipartFile boardContentImg;

    public Board toEntity(String savedFileName) { // 파일 이름을 매핑
        return Board.builder()
                .title(title)
                .content(content)
                .boardContentImg(savedFileName)
                .build();
    }
}
