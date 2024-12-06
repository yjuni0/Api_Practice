package com.myproject.Board.dto;

import com.myproject.Board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor //기본생성자
@AllArgsConstructor // 모든 필드값을 파라미터로 받는 생성자
@Data
public class AddBoardRequest {
    private String title;
    private String content;
    private MultipartFile boardContentImg;

    public Board toEntity(){ //생성자를 사용해 객체 생성
        return Board.builder()
                .title(title)
                .content(content)
                .boardContentImg(boardContentImg != null ? boardContentImg.getOriginalFilename() : null)
                .build();
    }

}
