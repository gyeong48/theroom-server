package com.theroom.server.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {

    private Long id;
    private String mainAddress;
    private String detailAddress;
    private String postCode;
    private double latitude;
    private double longitude;
    private String representative;
    private String email;
    private String phoneNumber;
}
