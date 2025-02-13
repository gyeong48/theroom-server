package com.theroom.server.domain.entity;

import com.theroom.server.domain.request.PortfolioModifyRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "thumbnail_id")
    private ThumbnailFile thumbnailFile;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "portfolio_id")
    @Builder.Default
    private List<PortfolioImageFile> portfolioImageFiles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private InteriorType interiorType;

    @Enumerated(EnumType.STRING)
    private BuildingType buildingType;

    private String title;
    private int supplyArea;

    private int completion;
    private LocalDate startDate;
    private LocalDate endDate;
    private int budget;

    public void modifyPortfolio(PortfolioModifyRequest request) {
        this.title = request.getTitle();
        this.supplyArea = request.getSupplyArea();
        this.completion = request.getCompletion();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.budget = request.getBudget();
        this.buildingType = request.getBuildingType();
        this.interiorType = request.getInteriorType();
        this.address = Address.builder()
                .mainAddress(request.getMainAddress())
                .detailAddress(request.getDetailAddress())
                .postCode(request.getPostCode())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();
    }

    public void clearPortfolioImageFiles() {
        this.portfolioImageFiles.clear();
    }

    public void changeThumbnailFile(ThumbnailFile thumbnailFile) {
        this.thumbnailFile = thumbnailFile;
    }

    public void addImageFile(PortfolioImageFile file) {
        this.portfolioImageFiles.add(file);
    }

}
