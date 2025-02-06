package com.theroom.server.service;

import com.theroom.server.domain.entity.Content;
import com.theroom.server.domain.entity.ContentType;
import com.theroom.server.domain.request.ContentRequest;
import com.theroom.server.domain.response.ContentResponse;
import com.theroom.server.repository.ContentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    public List<ContentResponse> getAbouts() {
        return contentRepository.findAllByContentType(ContentType.ABOUT)
                .stream()
                .map(c -> new ContentResponse(c.getId(), c.getStr()))
                .toList();
    }

    public List<ContentResponse> getService() {
        return contentRepository.findAllByContentType(ContentType.SERVICE)
                .stream()
                .map(c -> new ContentResponse(c.getId(), c.getStr()))
                .toList();
    }

    @Transactional
    public void addAboutContent(List<ContentRequest> requests) {
        for (int i = 0; i < requests.size(); i++) {
            ContentRequest contentRequest = requests.get(i);
            Long id = contentRequest.getId();
            String str = contentRequest.getStr();

            if (id != null) {
                Content content = contentRepository.findById(id).orElseThrow();
                content.changeOrd(i);
                content.changeStr(str);
                continue;
            }

            Content content = Content.builder()
                    .ord(i)
                    .str(str)
                    .contentType(ContentType.ABOUT)
                    .build();

            contentRepository.save(content);
        }
    }

    @Transactional
    public void addServiceContent(List<ContentRequest> requests) {
        for (int i = 0; i < requests.size(); i++) {
            ContentRequest contentRequest = requests.get(i);
            Long id = contentRequest.getId();
            String str = contentRequest.getStr();

            if (id != null) {
                Content content = contentRepository.findById(id).orElseThrow();
                content.changeOrd(i);
                content.changeStr(str);
                continue;
            }

            Content content = Content.builder()
                    .ord(i)
                    .str(str)
                    .contentType(ContentType.SERVICE)
                    .build();

            contentRepository.save(content);
        }
    }
}
