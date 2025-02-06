package com.theroom.server.controller;

import com.theroom.server.domain.request.ContentRequest;
import com.theroom.server.domain.response.ContentResponse;
import com.theroom.server.service.ContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
@Log4j2
public class ContentController {

    private final ContentService contentService;

    @PostMapping("/about")
    public ResponseEntity<Map<String, String>> addAbout(@RequestBody List<ContentRequest> requests) {
        return new ResponseEntity<>(Map.of("message", "ok"), HttpStatus.OK);
    }

    @GetMapping("/about")
    public ResponseEntity<Map<String, List<ContentResponse>>> getAbout() {
        List<ContentResponse> result = contentService.getAbouts();
        return new ResponseEntity<>(Map.of("data", result), HttpStatus.OK);
    }

    @PostMapping("/service")
    public ResponseEntity<Map<String, String>> addService(@RequestBody List<ContentRequest> requests) {
        return new ResponseEntity<>(Map.of("message", "ok"), HttpStatus.OK);
    }

    @GetMapping("/service")
    public ResponseEntity<Map<String, List<ContentResponse>>> getService() {
        List<ContentResponse> result = contentService.getService();
        return new ResponseEntity<>(Map.of("data", result), HttpStatus.OK);
    }

    @PostMapping("/main")
    public ResponseEntity<Map<String, String>> addMain() {
        return new ResponseEntity<>(Map.of("message", "ok"), HttpStatus.OK);
    }
}
