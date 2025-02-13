package com.theroom.server.domain.response;

import com.theroom.server.domain.entity.BuildingType;
import com.theroom.server.domain.entity.InteriorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioModifyDetailResponse {
    private Long id;
    private String title;
    private BuildingType buildingType;
    private int supplyArea;
    private int completion;
    private double latitude;
    private double longitude;
    private String postCode;
    private String mainAddress;
    private String detailAddress;
    private LocalDate startDate;
    private LocalDate endDate;
    private int budget;
    private InteriorType interiorType;
    private SimpleFile thumbnail;

    @Builder.Default
    private List<SimpleFile> imageFiles = new ArrayList<>();
}
