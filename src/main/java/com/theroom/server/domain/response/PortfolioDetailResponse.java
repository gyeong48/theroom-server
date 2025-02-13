package com.theroom.server.domain.response;

import com.theroom.server.domain.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDetailResponse {
    private Long id;
    private String mainAddress;
    private InteriorType interiorType;
    private BuildingType buildingType;
    private String title;
    private int supplyArea;
    long diffWeek;
    private int budget;
    private int completion;

    @Builder.Default
    private List<String> portfolioImageFilenames = new ArrayList<>();
}
