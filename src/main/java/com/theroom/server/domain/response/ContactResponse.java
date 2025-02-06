package com.theroom.server.domain.response;

import com.theroom.server.domain.entity.ProcessStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {

    private Long id;
    private String customer;
    private String mainAddress;
    private String detailAddress;
    private ProcessStatus status;
}
