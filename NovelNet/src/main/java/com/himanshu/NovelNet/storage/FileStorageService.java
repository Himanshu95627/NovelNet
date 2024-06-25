package com.himanshu.NovelNet.storage;

import com.himanshu.NovelNet.book.Book;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {
    @Value("${application.file.uploads.photos.path}")
    private String fileUploadPath;

    public String saveFile(@NonNull MultipartFile file, @NonNull Book book, @NonNull Long userId) {
        final String fileUploadSubPath = "users" + separator + userId;

        return uploadFile(file, fileUploadSubPath);
    }

    private String uploadFile(@NonNull MultipartFile file, @NonNull String fileUploadSubPath) {
        final String finalUploadPath = fileUploadPath + separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);

        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdirs();

            if (!folderCreated) {
                log.warn("can not create folder");
                return null;
            }
        }

        final String fileExtension = getFileExtension(file.getOriginalFilename());

        String targetFilePath = finalUploadPath + separator + System.currentTimeMillis() + "." + fileExtension;
        Path path = Paths.get(targetFilePath);
        try {
            Files.write(path, file.getBytes());
            log.info("File saved to location {}", targetFilePath);
        } catch (IOException e) {
            log.error("File not uploaded due to error {}", e);
        }
        return targetFilePath;
    }

    private String getFileExtension(String originalFilename) {
        if (originalFilename == null || originalFilename.isEmpty()) {
            return null;
        }
        int lastedIndexOf = originalFilename.lastIndexOf('.');
        if (lastedIndexOf == -1) {
            return null;
        }
        return originalFilename.substring(lastedIndexOf + 1).toLowerCase();
    }
}
