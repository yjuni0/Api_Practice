package com.myproject.Board.service;

import com.myproject.Board.dto.UpdateBoardRequest;
import com.myproject.Board.entity.Board;
import com.myproject.Board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시글 추가 메서드
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    // 전체 게시글 가져오기
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    // 게시글 상세 조회
    public Board findById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다."));
    }

    // 게시글 삭제
    public boolean delete(Long id) {
        boardRepository.deleteById(id);
        return true;
    }

    // 게시글 수정
    @Transactional
    public Board update(Long id, UpdateBoardRequest request) {
        Board existingBoard = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다."));

        // DTO의 null 값을 처리
        if (request.getTitle() != null) {
            existingBoard.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            existingBoard.setContent(request.getContent());
        }
        if (request.getBoardContentImg() != null) {
            existingBoard.setBoardContentImg(request.getBoardContentImg());
        }

        return existingBoard; // @Transactional 덕분에 save 호출 없이도 자동 저장
    }
}
