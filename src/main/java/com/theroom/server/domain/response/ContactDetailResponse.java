package com.theroom.server.domain.response;

import com.theroom.server.domain.entity.BuildingType;
import com.theroom.server.domain.entity.InteriorType;
import com.theroom.server.domain.entity.ProcessStatus;
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
public class ContactDetailResponse {

    private Long id;
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
    private ProcessStatus status;
    private String memo;
    private String customerMemo;

    @Builder.Default
    private List<SimpleFile> files = new ArrayList<>();
}
