package com.anervea.employee.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
    private String secretKey;
    private Long corporateId;
    private Long employeeId;
}
