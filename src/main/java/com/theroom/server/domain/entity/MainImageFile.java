package com.theroom.server.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@Table(name = "tb_main_image_file")
public class MainImageFile extends ReferenceFile {
    private String type;
    private String ord;

    public void setOrd(String ord) {
        this.ord = ord;
    }
}
