package com.theroom.server.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String str;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private int ord;

    public void changeStr(String str) {
        this.str = str;
    }

    public void changeOrd(int ord) {
        this.ord = ord;
    }

    public void changeTitle(String title) {
        this.title = title;
    }
}
