package com.myproject.Board.service;

import com.myproject.Board.entity.Board;
import com.myproject.Board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
@RequiredArgsConstructor
@Service
public class FileService {

    private final BoardService boardService;
    private final BoardRepository boardRepository;
    // 실제 파일 시스템 경로로 저장
    private static final String UPLOAD_DIR = "/Users/yejun/Desktop/Project/Level1/Board/src/main/resources/static/images/";

    public String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return "default-image.jpg"; // 기본 이미지
        }

        // 디렉토리가 없으면 생성
        File uploadPath = new File(UPLOAD_DIR);

        String fileName = file.getOriginalFilename();

        File destination = new File(uploadPath, fileName);

        // 디버깅: 저장 경로 확인
        System.out.println("Saving file to: " + destination.getAbsolutePath());

        file.transferTo(destination);

        return fileName; // 클라이언트가 접근할 경로 반환
    }
    @Transactional
    public boolean deleteFile(Long id) throws IOException {
        // 1. 데이터베이스에서 게시글 조회
        Board board = boardService.findById(id);
        if (board == null) {
            return false; // 게시글이 없으면 false 반환
        }

        // 2. 파일 경로 가져오기
        String boardContentImg = board.getBoardContentImg();

        if (boardContentImg != null && !boardContentImg.equals("default-image.jpg")) {
            // 3. 파일 삭제 처리
            File file = new File("/Users/yejun/Desktop/Project/Level1/Board/src/main/resources/static/images/" + boardContentImg);
            if (file.exists()) {
                if (!file.delete()) {
                    throw new IOException("Failed to delete file: " + file.getAbsolutePath());
                }
            } else {
                System.out.println("File not found: " + file.getAbsolutePath());
            }
        }

        // 4. 게시글 삭제
        boardService.delete(id);

        return true;
    }

}
