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
@Table(name = "tb_document_file")
public class DocumentFile extends ReferenceFile {
    private String type;
}
