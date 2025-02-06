package com.theroom.server.domain.request;

import com.theroom.server.domain.entity.BuildingType;
import com.theroom.server.domain.entity.InteriorType;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioAddRequest {

    private String title;
    private BuildingType buildingType;
    private int supplyArea;
    private int exclusiveArea;
    private double latitude;
    private double longitude;
    private String postCode;
    private String mainAddress;
    private String detailAddress;
    private LocalDate startDate;
    private LocalDate endDate;
    private int budget;
    private InteriorType interiorType;
}
