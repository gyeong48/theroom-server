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
@Table(name = "tb_customer_file")
public class CustomerFile extends ReferenceFile {
    private String type;
    private String ord;

    public void setOrd(String ord) {
        this.ord = ord;
    }
}
