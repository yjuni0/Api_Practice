package com.myproject.Board.service;

import com.myproject.Board.entity.Board;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Service
@Transactional
public class FileService {



    // 실제 파일 시스템 경로로 저장
    private static final String UPLOAD_DIR = "/Users/yejun/Desktop/Project/Level1/Board/src/main/resources/static/images/";

    public String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return "default-image.jpg"; // 기본 이미지
        }

        // 디렉토리가 없으면 생성
        File uploadPath = new File(UPLOAD_DIR);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        String originalFileName = file.getOriginalFilename(); // 원본 파일 이름
        String fileName = originalFileName;
        String fileExtension = ""; // 파일 확장자

        // 확장자 분리
        int dotIndex = originalFileName.lastIndexOf(".");
        if (dotIndex > 0) {
            fileName = originalFileName.substring(0, dotIndex); // 확장자를 제외한 파일 이름
            fileExtension = originalFileName.substring(dotIndex); // 확장자
        }

        File destination = new File(uploadPath, originalFileName);
        int count = 1;

        // 동일한 이름의 파일이 있으면 숫자 붙이기
        while (destination.exists()) {
            String newFileName = fileName + "(" + count + ")" + fileExtension;
            destination = new File(uploadPath, newFileName);
            count++;
        }

        // 디버깅: 저장 경로 확인
        System.out.println("Saving file to: " + destination.getAbsolutePath());

        file.transferTo(destination);

        return destination.getName(); // 저장된 파일 이름 반환
    }

    public boolean deleteFile(String fileName) throws IOException {
        // 파일 경로 가져오기
        File file = new File(UPLOAD_DIR + fileName);
        if (file.exists()) {
            if (!file.delete()) {
                throw new IOException("Failed to delete file: " + file.getAbsolutePath());
            }
            return true;
        } else {
            System.out.println("File not found: " + file.getAbsolutePath());
            return false;
        }
    }
}
