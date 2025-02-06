package com.theroom.server.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_portfolio_image_file")
public class PortfolioImageFile extends ReferenceFile {
    private String type;
    private int ord;

    public void setOrd(int ord) {
        this.ord = ord;
    }
}
