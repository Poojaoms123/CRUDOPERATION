package com.anervea.employee.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MobileAuthenticationRequest {
   private String mobileNumber;
    private int mobileOtp;
}
