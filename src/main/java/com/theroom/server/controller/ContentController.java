package com.theroom.server.controller;

import com.theroom.server.domain.request.CompanyRequest;
import com.theroom.server.domain.request.ContentRequest;
import com.theroom.server.domain.request.MainContentRequest;
import com.theroom.server.domain.response.CompanyResponse;
import com.theroom.server.domain.response.ContentResponse;
import com.theroom.server.domain.response.SimpleFile;
import com.theroom.server.service.ContentService;
import com.theroom.server.util.LocalFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
@Log4j2
public class ContentController {

    private final ContentService contentService;
    private final LocalFileUtil fileUtil;

    @PostMapping("/main")
    public ResponseEntity<Map<String, String>> addMain(MainContentRequest request, List<MultipartFile> imageFiles) {
        contentService.addMainImages(request.getUploadImageFilenames(), imageFiles);
        return new ResponseEntity<>(Map.of("message", "ok"), HttpStatus.OK);
    }

    @GetMapping("/main")
    public ResponseEntity<Map<String, List<SimpleFile>>> getMain() {
        List<SimpleFile> result = contentService.getMainImages();
        return new ResponseEntity<>(Map.of("data", result), HttpStatus.OK);
    }

    @PostMapping("/about")
    public ResponseEntity<Map<String, String>> addAbout(@RequestBody List<ContentRequest> requests) {
        contentService.addAboutContent(requests);
        return new ResponseEntity<>(Map.of("message", "ok"), HttpStatus.OK);
    }

    @GetMapping("/about")
    public ResponseEntity<Map<String, List<ContentResponse>>> getAbout() {
        List<ContentResponse> result = contentService.getAbouts();
        return new ResponseEntity<>(Map.of("data", result), HttpStatus.OK);
    }

    @PostMapping("/service")
    public ResponseEntity<Map<String, String>> addService(@RequestBody List<ContentRequest> requests) {
        contentService.addServiceContent(requests);
        return new ResponseEntity<>(Map.of("message", "ok"), HttpStatus.OK);
    }

    @GetMapping("/service")
    public ResponseEntity<Map<String, List<ContentResponse>>> getService() {
        List<ContentResponse> result = contentService.getService();
        return new ResponseEntity<>(Map.of("data", result), HttpStatus.OK);
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable(name = "contentId") Long contentId) {
        contentService.deleteContent(contentId);
        return new ResponseEntity<>(Map.of("message", "ok"), HttpStatus.OK);
    }

    @GetMapping("/company")
    public ResponseEntity<Map<String, CompanyResponse>> getCompany() {
        CompanyResponse result = contentService.getCompany();
        return new ResponseEntity<>(Map.of("data", result), HttpStatus.OK);
    }

    @PostMapping("/company")
    public ResponseEntity<Map<String, String>> addCompany(@RequestBody CompanyRequest request) {
        contentService.addCompany(request);
        return new ResponseEntity<>(Map.of("message", "ok"), HttpStatus.OK);
    }

    @GetMapping("/view/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable(name = "filename") String filename) {
        Resource file = fileUtil.getFile(filename);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }
}
