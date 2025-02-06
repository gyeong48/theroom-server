package com.theroom.server.util;

import com.theroom.server.domain.entity.PortfolioImageFile;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
public class CustomFileUtil {

    @Value("${upload.path}")
    private String path;

    @PostConstruct
    public void init() {
        File tempFolder = new File(path);

        if (!tempFolder.exists()) {
            tempFolder.mkdir();
        }

        path = tempFolder.getAbsolutePath();
    }

    /**
     * Resource에 대한 공부 필요
     */
    public Resource getFile(String filename) {
        FileSystemResource resource = new FileSystemResource(path + File.separator + filename);

        if (!resource.isReadable()) {
            resource = new FileSystemResource(path + File.separator + filename);
        }

        return resource;
    }

    public String saveFile(MultipartFile file) {
        String savedFilename = null;

        String saveFilename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path savePath = Paths.get(path, saveFilename);

        try {
            Files.copy(file.getInputStream(), savePath);
            savedFilename = saveFilename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return savedFilename;
    }

    public List<String> saveFiles(List<MultipartFile> files) {
        List<String> savedFilenames = new ArrayList<>();

        for (MultipartFile file : files) {
            String saveFilename = saveFile(file);
            savedFilenames.add(saveFilename);
        }

        return savedFilenames;
    }

    public void deleteFile(String filename) {
        Path filePath = Paths.get(path, filename);

        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteFiles(List<PortfolioImageFile> files) {
        for (PortfolioImageFile file : files) {
            deleteFile(file.getName());
        }
    }
}
