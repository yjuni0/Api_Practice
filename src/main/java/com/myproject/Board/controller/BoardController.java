package com.myproject.Board.controller;

import com.myproject.Board.dto.AddBoardRequest;
import com.myproject.Board.dto.BoardResponse;
import com.myproject.Board.dto.UpdateBoardRequest;
import com.myproject.Board.entity.Board;
import com.myproject.Board.service.BoardService;
import com.myproject.Board.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;

    // 게시글 등록
    @PostMapping("/api/board")
    public ResponseEntity<String> addBoard(@ModelAttribute AddBoardRequest request) {
        try {
            // 파일 저장 처리
            String savedFileName = fileService.saveFile(request.getBoardContentImg());
            // 엔티티 생성 및 저장
            Board board = request.toEntity(savedFileName);
            boardService.save(board);

            return ResponseEntity.ok("Board added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add board.");
        }
    }

    // 게시글 전체 조회

    @GetMapping("/api/board")
    public ResponseEntity<List<BoardResponse>> findAllBoards() {
        List<BoardResponse> boards = boardService.findAll()
                .stream()
                .map(BoardResponse::new)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }

    // 게시글 상세 조회
    @GetMapping("/api/board/{id}")
    public ResponseEntity<BoardResponse> findBoardById(@PathVariable("id") Long id) {
        Board board = boardService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new BoardResponse(board));
    }

    // 게시글 삭제
    @DeleteMapping("/api/board/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        try {
            boolean deleted = fileService.deleteFile(id);
            if (!deleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 게시글이 없으면 404 반환
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 성공 시 204 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 실패 시 500 반환
        }
    }
    // 게시글 수정
    @PatchMapping("/api/board/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable("id") Long id,
                                             @RequestParam("title") String title,
                                             @RequestParam("content") String content,
                                             @RequestParam(value = "boardContentImg", required = false) MultipartFile file) throws IOException {
        // 파일 처리
        String boardContentImg = null;
        if (file != null && !file.isEmpty()) {
            boardContentImg = fileService.saveFile(file);
        }

        // 수정 요청 처리
        UpdateBoardRequest request = new UpdateBoardRequest(title, content, boardContentImg);
        Board updatedBoard = boardService.update(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(updatedBoard);
    }
}
