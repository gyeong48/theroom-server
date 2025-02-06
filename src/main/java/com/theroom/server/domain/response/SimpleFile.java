package com.theroom.server.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleFile {

    private Long id;
    private int ord;
    private String name;
    private String uploadedName;
    private long size;
}
