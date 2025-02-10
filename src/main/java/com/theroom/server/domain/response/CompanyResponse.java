package com.theroom.server.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {

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
