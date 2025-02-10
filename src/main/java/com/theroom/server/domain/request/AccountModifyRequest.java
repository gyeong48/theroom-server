package com.theroom.server.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountModifyRequest {

    private String username;
    private String currentPassword;
    private String newPassword;
    private String newPasswordCheck;
}
