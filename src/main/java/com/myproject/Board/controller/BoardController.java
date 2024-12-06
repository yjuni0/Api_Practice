package com.myproject.Board.controller;

import com.myproject.Board.dto.AddBoardRequest;
import com.myproject.Board.dto.BoardResponse;
import com.myproject.Board.entity.Board;
import com.myproject.Board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //Http 메서드 Post일 때
    @PostMapping("/api/board")
    //@RequestBody 로 요청 본문 값 매핑
    public ResponseEntity<Board> addBoard(@RequestBody AddBoardRequest request){
        Board savedBoard = boardService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBoard);
    }

    @GetMapping("/api/board")
    public ResponseEntity<List<BoardResponse>> findAllBoards(){
        List<BoardResponse> boards = boardService.findAll()
                .stream()
                .map(BoardResponse::new)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }
    @GetMapping("/api/board/{id}")
    //url 경로에서 값 추출
    public ResponseEntity<BoardResponse> findBoardById(@PathVariable("id") Long id){
        Board board = boardService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new BoardResponse(board));
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("id") Long id){
        boardService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PatchMapping("/api/board/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable("id") Long id, @RequestBody AddBoardRequest request){
        Board updatedBoard = boardService.update(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(updatedBoard);
    }
}
