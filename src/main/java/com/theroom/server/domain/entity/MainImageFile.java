package com.theroom.server.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_main_image_file")
public class MainImageFile extends ReferenceFile {
    private String type;
    private int ord;

    public void setOrd(int ord) {
        this.ord = ord;
    }
}
