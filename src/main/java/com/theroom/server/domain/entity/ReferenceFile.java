package com.theroom.server.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Table(name = "tb_reference_file")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ReferenceFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String originalName;
    private long size;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
