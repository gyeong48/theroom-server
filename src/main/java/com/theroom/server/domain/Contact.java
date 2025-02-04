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
@Table(name = "tb_contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_id")
    @Builder.Default
    private List<DocumentFile> documentFiles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private BuildingType buildingType;

    @Enumerated(EnumType.STRING)
    private InteriorType interiorType;

    @Enumerated(EnumType.STRING)
    private ProcessStatus processStatus;

    private String username;
    private String email;
    private String phoneNumber;
    private int buildingArea;
    private boolean personalInformationAgree;
    private String memo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime startDate;
    private LocalDateTime moveInDate;
    private int budget;
}
