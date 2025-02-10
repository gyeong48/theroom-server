package com.theroom.server.service;

import com.theroom.server.domain.entity.*;
import com.theroom.server.domain.request.PortfolioAddRequest;
import com.theroom.server.domain.request.PortfolioModifyRequest;
import com.theroom.server.domain.response.PortfolioDetailResponse;
import com.theroom.server.domain.response.PortfolioModifyDetailResponse;
import com.theroom.server.domain.response.PortfolioResponse;
import com.theroom.server.domain.response.SimpleFile;
import com.theroom.server.repository.PortfolioRepository;
import com.theroom.server.util.LocalFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final LocalFileUtil localFileUtil;

    @Transactional
    public void addPortfolio(PortfolioAddRequest request, MultipartFile thumbnail, List<MultipartFile> imageFiles) {
        ThumbnailFile thumbnailFile = null;
        List<PortfolioImageFile> portfolioImageFiles = new ArrayList<>();

        if (thumbnail != null) {
            thumbnailFile = ThumbnailFile.builder()
                    .name(localFileUtil.saveFile(thumbnail))
                    .originalName(thumbnail.getOriginalFilename())
                    .size(thumbnail.getSize())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .type(thumbnail.getContentType())
                    .build();
        }

        if (imageFiles != null) {
            for (MultipartFile file : imageFiles) {
                PortfolioImageFile portfolioImageFile = PortfolioImageFile.builder()
                        .name(localFileUtil.saveFile(file))
                        .originalName(file.getOriginalFilename())
                        .size(file.getSize())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .type(file.getContentType())
                        .build();

                portfolioImageFiles.add(portfolioImageFile);
            }
        }

        Portfolio portfolio = Portfolio.builder()
                .title(request.getTitle())
                .buildingType(request.getBuildingType())
                .supplyArea(request.getSupplyArea())
                .exclusiveArea(request.getExclusiveArea())
                .budget(request.getBudget())
                .interiorType(request.getInteriorType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .address(Address.builder()
                        .mainAddress(request.getMainAddress())
                        .detailAddress(request.getDetailAddress())
                        .postCode(request.getPostCode())
                        .latitude(request.getLatitude())
                        .longitude(request.getLongitude())
                        .build()
                )
                .thumbnailFile(thumbnailFile)
                .portfolioImageFiles(portfolioImageFiles)
                .build();

        portfolioRepository.save(portfolio);
    }

    /**
     * 나중에 페이지네이션 적용 필요
     * PageRequest를 따로 만들어서 그안에 list로 담고, 페이지에 필요한 정보를 포함하여 클라이언트로 전송 예정
     */
    public List<PortfolioResponse> getList() {
        return portfolioRepository.findAllWithThumbnail()
                .stream()
                .map(p -> PortfolioResponse.builder()
                        .id(p.getId())
                        .title(p.getTitle())
                        .filename(p.getThumbnailFile().getName())
                        .build()
                )
                .toList();
    }

    public PortfolioDetailResponse getDetail(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findByIdWithImageList(portfolioId).orElseThrow();
        LocalDate startDate = portfolio.getStartDate() != null ? portfolio.getStartDate() : LocalDate.now();
        LocalDate endDate = portfolio.getEndDate() != null ? portfolio.getEndDate() : LocalDate.now();

        return PortfolioDetailResponse.builder()
                .id(portfolio.getId())
                .mainAddress(portfolio.getAddress().getMainAddress())
                .interiorType(portfolio.getInteriorType())
                .buildingType(portfolio.getBuildingType())
                .title(portfolio.getTitle())
                .supplyArea(portfolio.getSupplyArea())
                .exclusiveArea(portfolio.getExclusiveArea())
                .diffWeek(ChronoUnit.WEEKS.between(startDate, endDate))
                .budget(portfolio.getBudget())
                .portfolioImageFilenames(portfolio.getPortfolioImageFiles()
                        .stream()
                        .map(ReferenceFile::getName)
                        .toList()
                )
                .build();
    }

    public PortfolioModifyDetailResponse getModifyDetail(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findByIdWithAll(portfolioId).orElseThrow();

        return PortfolioModifyDetailResponse.builder()
                .id(portfolio.getId())
                .mainAddress(portfolio.getAddress().getMainAddress())
                .detailAddress(portfolio.getAddress().getDetailAddress())
                .postCode(portfolio.getAddress().getPostCode())
                .latitude(portfolio.getAddress().getLatitude())
                .longitude(portfolio.getAddress().getLongitude())
                .interiorType(portfolio.getInteriorType())
                .buildingType(portfolio.getBuildingType())
                .title(portfolio.getTitle())
                .supplyArea(portfolio.getSupplyArea())
                .exclusiveArea(portfolio.getExclusiveArea())
                .startDate(portfolio.getStartDate())
                .endDate(portfolio.getEndDate())
                .budget(portfolio.getBudget())
                .thumbnail(SimpleFile.builder()
                        .id(portfolio.getThumbnailFile().getId())
                        .name(portfolio.getThumbnailFile().getOriginalName())
                        .uploadedName(portfolio.getThumbnailFile().getName())
                        .size(portfolio.getThumbnailFile().getSize())
                        .build()
                )
                .imageFiles(portfolio.getPortfolioImageFiles()
                        .stream()
                        .map(pi -> SimpleFile.builder()
                                .id(pi.getId())
                                .ord(pi.getOrd())
                                .size(pi.getSize())
                                .name(pi.getOriginalName())
                                .uploadedName(pi.getName())
                                .build()
                        )
                        .toList()
                )
                .build();
    }

    @Transactional
    public void modify(Long portfolioId, PortfolioModifyRequest request, MultipartFile thumbnail, List<MultipartFile> imageFiles) {
        Portfolio savedPortfolio = portfolioRepository.findByIdWithAll(portfolioId).orElseThrow();

        //thumbnail 변경
        if (thumbnail != null) {
            ThumbnailFile thumbnailFile = ThumbnailFile.builder()
                    .name(localFileUtil.saveFile(thumbnail))
                    .originalName(thumbnail.getOriginalFilename())
                    .size(thumbnail.getSize())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .type(thumbnail.getContentType())
                    .build();

            //기존 thumbnail 이미지 삭제
            localFileUtil.deleteFile(savedPortfolio.getThumbnailFile().getName());

            //portfolio entity에 새로운 값이 대체 되면서 orphanremoval 속성에 의해 자동으로 delete문이 실행된다.
            savedPortfolio.changeThumbnailFile(thumbnailFile);
        }

        //수정시에 제외된 이미지파일 필터링
        List<PortfolioImageFile> deletedImageFiles = savedPortfolio.getPortfolioImageFiles()
                .stream()
                .filter(pi -> !request.getUploadImageFilenames().contains(pi.getName()))
                .toList();

        //이미지 삭제
        localFileUtil.deleteFiles(deletedImageFiles);

        //수정시 제외되지 않은 이미지 파일 수정시 제외된 이미지 파일을 리스트에서 개별 분리를 못하므로
        //전부 삭제후 다시 값을 넣어준다. orphanRemoval 속성을 사용할 때 List는 그 자체를 새로 대체하면 안되고(단일 값은 가능), 객체를 추가하거나 빼야 한다. 그래야 오류가 안남
        List<PortfolioImageFile> maintainedImageFiles = savedPortfolio.getPortfolioImageFiles()
                .stream()
                .filter(pi -> request.getUploadImageFilenames().contains(pi.getName()))
                .toList();

        //기존 리스트 초기화
        savedPortfolio.clearPortfolioImageFiles();

        //재 저장
        savedPortfolio.getPortfolioImageFiles().addAll(maintainedImageFiles);

        //imageFiles 저장 및 변환
        if (imageFiles != null) {
            for (MultipartFile file : imageFiles) {
                PortfolioImageFile portfolioImageFile = PortfolioImageFile.builder()
                        .name(localFileUtil.saveFile(file))
                        .originalName(file.getOriginalFilename())
                        .size(file.getSize())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .type(file.getContentType())
                        .build();

                savedPortfolio.addImageFile(portfolioImageFile);
            }
        }

        //파일 이외 값들 저장
        savedPortfolio.modifyPortfolio(request);
    }

    @Transactional
    public void deletePortfolio(Long portfolioId) {
        Portfolio savedPortfolio = portfolioRepository.findByIdWithAllImage(portfolioId).orElseThrow();
        ThumbnailFile thumbnailFile = savedPortfolio.getThumbnailFile();
        List<PortfolioImageFile> portfolioImageFiles = savedPortfolio.getPortfolioImageFiles();

        if (thumbnailFile != null) {
            localFileUtil.deleteFile(thumbnailFile.getName());
        }

        if (portfolioImageFiles != null) {
            localFileUtil.deleteFiles(portfolioImageFiles);
        }

        portfolioRepository.deleteById(portfolioId);
    }
}
