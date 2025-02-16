package com.theroom.server.domain.entity;

import com.theroom.server.domain.request.ContactModifyRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_id")
    @Builder.Default
    private List<CustomerFile> customerFiles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private BuildingType buildingType;

    @Enumerated(EnumType.STRING)
    private InteriorType interiorType;

    @Enumerated(EnumType.STRING)
    private ProcessStatus processStatus;

    private String customer;
    private String email;
    private String phoneNumber;
    private int exclusiveArea;
    private boolean personalInformationAgree;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate startDate;
    private LocalDate moveInDate;
    private int budget;

    @Lob
    private String customerMemo;

    @Lob
    private String memo;

    public void modify(ContactModifyRequest request) {
        this.customer = request.getCustomer();
        this.email = request.getEmail();
        this.phoneNumber = request.getPhoneNumber();
        this.buildingType = request.getBuildingType();
        this.exclusiveArea = request.getExclusiveArea();
        this.budget = request.getBudget();
        this.interiorType = request.getInteriorType();
        this.startDate = request.getStartDate();
        this.moveInDate = request.getMoveInDate();
        this.address = Address.builder()
                .mainAddress(request.getMainAddress())
                .detailAddress(request.getDetailAddress())
                .postCode(request.getPostCode())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();
        this.processStatus = request.getStatus();
        this.memo = request.getMemo();
        this.customerMemo = request.getCustomerMemo();
        this.updatedAt = LocalDateTime.now();
    }
}
