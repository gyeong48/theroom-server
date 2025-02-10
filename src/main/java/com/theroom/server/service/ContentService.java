package com.theroom.server.service;

import com.theroom.server.domain.entity.*;
import com.theroom.server.domain.request.CompanyRequest;
import com.theroom.server.domain.request.ContentRequest;
import com.theroom.server.domain.response.CompanyResponse;
import com.theroom.server.domain.response.ContentResponse;
import com.theroom.server.domain.response.SimpleFile;
import com.theroom.server.repository.CompanyRepository;
import com.theroom.server.repository.ContentRepository;
import com.theroom.server.repository.MainImageFileRepository;
import com.theroom.server.util.LocalFileUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ContentService {

    private final ContentRepository contentRepository;
    private final MainImageFileRepository mainImageFileRepository;
    private final CompanyRepository companyRepository;
    private final LocalFileUtil localFileUtil;

    public List<ContentResponse> getAbouts() {
        return contentRepository.findAllByContentType(ContentType.ABOUT)
                .stream()
                .map(c -> new ContentResponse(c.getId(), c.getTitle(), c.getStr()))
                .toList();
    }

    public List<ContentResponse> getService() {
        return contentRepository.findAllByContentType(ContentType.SERVICE)
                .stream()
                .map(c -> new ContentResponse(c.getId(), c.getTitle(), c.getStr()))
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
            String title = contentRequest.getTitle();
            String str = contentRequest.getStr();

            if (id != null) {
                Content content = contentRepository.findById(id).orElseThrow();
                content.changeOrd(i);
                content.changeTitle(title);
                content.changeStr(str);
                continue;
            }

            Content content = Content.builder()
                    .ord(i)
                    .str(str)
                    .title(title)
                    .contentType(ContentType.SERVICE)
                    .build();

            contentRepository.save(content);
        }
    }

    public void deleteContent(Long contentId) {
        contentRepository.deleteById(contentId);
    }

    public List<SimpleFile> getMainImages() {
        return mainImageFileRepository.findAll()
                .stream()
                .map(m -> SimpleFile.builder()
                        .id(m.getId())
                        .name(m.getOriginalName())
                        .uploadedName(m.getName())
                        .size(m.getSize())
                        .build()
                )
                .toList();
    }

    @Transactional
    public void addMainImages(List<String> uploadImageFilenames, List<MultipartFile> imageFiles) {
        log.info("addMainImages");
        List<MainImageFile> foundImageFiles = mainImageFileRepository.findAll();

        List<MainImageFile> deletedImageFiles = foundImageFiles.stream()
                .filter(mi -> !uploadImageFilenames.contains(mi.getName()))
                .toList();

        if (!deletedImageFiles.isEmpty()) {
            localFileUtil.deleteFiles(deletedImageFiles);
            mainImageFileRepository.deleteAll(deletedImageFiles);
        }

        if (imageFiles != null) {
            for (MultipartFile file : imageFiles) {
                MainImageFile mainImageFile = MainImageFile.builder()
                        .name(localFileUtil.saveFile(file))
                        .originalName(file.getOriginalFilename())
                        .size(file.getSize())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .type(file.getContentType())
                        .build();

                mainImageFileRepository.save(mainImageFile);
            }
        }
    }

    @Transactional
    public void addCompany(CompanyRequest request) {
        if (request.getId() == null) {
            Company company = Company.builder()
                    .email(request.getEmail())
                    .representative(request.getRepresentative())
                    .phoneNumber(request.getPhoneNumber())
                    .address(Address.builder()
                            .mainAddress(request.getMainAddress())
                            .detailAddress(request.getDetailAddress())
                            .postCode(request.getPostCode())
                            .latitude(request.getLatitude())
                            .longitude(request.getLongitude())
                            .build()
                    )
                    .build();

            companyRepository.save(company);
        } else {
            Company savedCompany = companyRepository.findById(request.getId()).orElseThrow();
            Address savedCompanyAddress = savedCompany.getAddress();

            savedCompanyAddress.changeMainAddress(request.getMainAddress());
            savedCompanyAddress.changeDetailAddress(request.getDetailAddress());
            savedCompanyAddress.changePostCode(request.getPostCode());
            savedCompanyAddress.changeLatitude(request.getLatitude());
            savedCompanyAddress.changeLongitude(request.getLongitude());

            savedCompany.changeEmail(request.getEmail());
            savedCompany.changePhoneNumber(request.getPhoneNumber());
            savedCompany.changeRepresentative(request.getRepresentative());
            savedCompany.changeAddress(savedCompanyAddress);
        }
    }

    public CompanyResponse getCompany() {
        List<Company> companies = companyRepository.findAll();

        if (companies.isEmpty()) {
            return CompanyResponse.builder()
                    .id(null)
                    .email("")
                    .representative("")
                    .phoneNumber("")
                    .mainAddress("")
                    .detailAddress("")
                    .postCode("")
                    .latitude(0)
                    .longitude(0)
                    .build();
        }

        Company company = companies.stream().findFirst().orElseThrow();

        return CompanyResponse.builder()
                .id(company.getId())
                .email(company.getEmail())
                .representative(company.getRepresentative())
                .phoneNumber(company.getPhoneNumber())
                .mainAddress(company.getAddress().getMainAddress())
                .detailAddress(company.getAddress().getDetailAddress())
                .postCode(company.getAddress().getPostCode())
                .latitude(company.getAddress().getLatitude())
                .longitude(company.getAddress().getLongitude())
                .build();
    }
}
