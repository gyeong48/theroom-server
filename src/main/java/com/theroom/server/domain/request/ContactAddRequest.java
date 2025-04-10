package com.theroom.server.domain.request;

import com.theroom.server.domain.entity.BuildingType;
import com.theroom.server.domain.entity.InteriorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactAddRequest {

    private String customer;
    private String email;
    private String phoneNumber;
    private BuildingType buildingType;
    private double exclusiveArea;
    private double latitude;
    private double longitude;
    private String postCode;
    private String mainAddress;
    private String detailAddress;
    private LocalDate startDate;
    private LocalDate moveInDate;
    private int budget;
    private InteriorType interiorType;
    private String customerMemo;
}
