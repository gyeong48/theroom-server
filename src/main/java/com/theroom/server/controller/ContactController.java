package com.theroom.server.controller;

import com.theroom.server.domain.request.ContactAddRequest;
import com.theroom.server.domain.request.ContactModifyRequest;
import com.theroom.server.domain.response.ContactDetailResponse;
import com.theroom.server.domain.response.ContactResponse;
import com.theroom.server.service.ContactService;
import com.theroom.server.util.LocalFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@Log4j2
public class ContactController {

    private final ContactService contactService;
    private final LocalFileUtil localFileUtil;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> add(ContactAddRequest request, List<MultipartFile> files) {
        contactService.addContact(request, files);
        return new ResponseEntity<>(Map.of("message", "ok"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, List<ContactResponse>>> getList() {
        List<ContactResponse> result = contactService.getList();
        return new ResponseEntity<>(Map.of("data", result), HttpStatus.OK);
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<Map<String, ContactDetailResponse>> getDetail(@PathVariable("contactId") Long contactId) {
        ContactDetailResponse result = contactService.getContactDetail(contactId);
        return new ResponseEntity<>(Map.of("data", result), HttpStatus.OK);
    }

    @PutMapping("/{contactId}/modify")
    public ResponseEntity<Map<String, String>> modify(@PathVariable("contactId") Long contactId, ContactModifyRequest request) {
        contactService.modifyContact(contactId, request);
        return new ResponseEntity<>(Map.of("message", "ok"), HttpStatus.OK);
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Map<String, String>> modify(@PathVariable("contactId") Long contactId) {
        contactService.deleteContact(contactId);
        return new ResponseEntity<>(Map.of("message", "ok"), HttpStatus.OK);
    }

    @GetMapping("/download/{filename}/{originalFilename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, @PathVariable String originalFilename) throws UnsupportedEncodingException {
        Resource resource = localFileUtil.downloadFile(filename);

        if (resource == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"")
                .body(resource);
    }
}
