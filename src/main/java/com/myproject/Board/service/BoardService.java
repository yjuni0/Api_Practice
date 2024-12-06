package com.myproject.Board.service;

import com.myproject.Board.dto.AddBoardRequest;
import com.myproject.Board.entity.Board;
import com.myproject.Board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.util.List;

@RequiredArgsConstructor // final 이 붙거나 @NotNull 이 붙은 필드의 생성자 추가
@Service // 스프링 빈으로 등록
public class BoardService {
    private final BoardRepository boardRepository; // DI 주입

    //게시글 글 추가 메서드
    public Board save(AddBoardRequest request) {
        return boardRepository.save(request.toEntity());
    }
    // 전체 게시글 리스트 타입으로 가져오는 메소드 구현
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    // 게시글 상세 페이지
    public Board findById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("등록된 게시글이 없습니다.")); // 해당 id 의 게시글이 없으면 예외처리 할 람다식
    }
    // 게시글 삭제 메서드 ( 삭제 로직은 리턴값이 없으므로 void )
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
    // 게시글 수정 메서드
    @Transactional
    public Board update(Long id, AddBoardRequest request) {
        Board existingBoard = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board id"));

        if (request.getTitle() != null) {
            existingBoard.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            existingBoard.setContent(request.getContent());
        }
        if (request.getBoardContentImg() != null) {
            existingBoard.setBoardContentImg(String.valueOf(request.getBoardContentImg()));
        }

        // 필요한 다른 필드들도 이와 같이 처리

        return boardRepository.save(existingBoard);
    }

}
