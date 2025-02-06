package com.theroom.server.controller;

import com.theroom.server.domain.request.PortfolioAddRequest;
import com.theroom.server.domain.request.PortfolioModifyRequest;
import com.theroom.server.domain.response.PortfolioDetailResponse;
import com.theroom.server.domain.response.PortfolioModifyDetailResponse;
import com.theroom.server.domain.response.PortfolioResponse;
import com.theroom.server.service.PortfolioService;
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

@Log4j2
@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final LocalFileUtil fileUtil;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> add(
            PortfolioAddRequest request,
            @RequestParam(required = false) MultipartFile thumbnail,
            @RequestParam(required = false) List<MultipartFile> imageFiles
    ) {
        portfolioService.addPortfolio(request, thumbnail, imageFiles);
        return new ResponseEntity<>(Map.of("message", "ok"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, List<PortfolioResponse>>> getList() {
        List<PortfolioResponse> result = portfolioService.getList();
        return new ResponseEntity<>(Map.of("data", result), HttpStatus.OK);
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<Map<String, PortfolioDetailResponse>> getDetail(@PathVariable(name = "portfolioId") Long portfolioId) {
        PortfolioDetailResponse result = portfolioService.getDetail(portfolioId);
        return new ResponseEntity<>(Map.of("data", result), HttpStatus.OK);
    }

    @GetMapping("/{portfolioId}/modify")
    public ResponseEntity<Map<String, PortfolioModifyDetailResponse>> getModifyDetail(@PathVariable(name = "portfolioId") Long portfolioId) {
        PortfolioModifyDetailResponse result = portfolioService.getModifyDetail(portfolioId);
        return new ResponseEntity<>(Map.of("data", result), HttpStatus.OK);
    }

    @PutMapping("/{portfolioId}/modify")
    public ResponseEntity<Map<String, String>> modify(
            @PathVariable(name = "portfolioId") Long portfolioId,
            PortfolioModifyRequest request,
            @RequestParam(required = false) MultipartFile thumbnail,
            @RequestParam(required = false) List<MultipartFile> imageFiles
    ) {
        portfolioService.modify(portfolioId, request, thumbnail, imageFiles);
        return new ResponseEntity<>(Map.of("message", "ok"), HttpStatus.OK);
    }

    @GetMapping("/view/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable(name = "filename") String filename) {
        Resource file = fileUtil.getFile(filename);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable(name = "portfolioId") Long portfolioId) {
        portfolioService.deletePortfolio(portfolioId);
        return new ResponseEntity<>(Map.of("message", "ok"), HttpStatus.OK);
    }
}
