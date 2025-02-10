package com.theroom.server.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mainAddress;
    private String detailAddress;
    private String postCode;
    private double latitude;
    private double longitude;

    public void changeMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public void changeDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public void changePostCode(String postCode) {
        this.postCode = postCode;
    }

    public void changeLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void changeLongitude(double longitude) {
        this.longitude = longitude;
    }
}
