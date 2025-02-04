package com.theroom.server.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "tb_portfolio_image_file")
public class PortfolioImageFile extends ReferenceFile {
    private String type;
}
