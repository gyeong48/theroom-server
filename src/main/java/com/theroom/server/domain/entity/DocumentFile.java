package com.theroom.server.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "tb_document_file")
public class DocumentFile extends ReferenceFile {
    private String type;
    private String ord;

    public void setOrd(String ord) {
        this.ord = ord;
    }
}
