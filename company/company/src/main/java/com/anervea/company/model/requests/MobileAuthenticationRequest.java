package com.anervea.company.model.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MobileAuthenticationRequest {
    private String mobileNumber;
    private int mobileOtp;
}
