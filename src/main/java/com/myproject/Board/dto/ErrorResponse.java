package com.myproject.Board.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {
    // getter
    private String message;

    // 생성자
    public ErrorResponse(String message) {
        this.message = message;
    }

    // setter
    public void setMessage(String message) {
        this.message = message;
    }
}
