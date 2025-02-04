package com.theroom.server.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Table(name = "tb_reference_file")
public abstract class ReferenceFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String originalName;
    private int size;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
