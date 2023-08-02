package com.anervea.company.model.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordRequest {
    private Long companyId;
    private String newPassword;
}
