package com.theroom.server.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    @OneToOne
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
    private int exclusiveArea;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int budget;
    private LocalDateTime completion;
}
