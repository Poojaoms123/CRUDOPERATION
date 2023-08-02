package com.anervea.company.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private Long corporateId;
    private String name;
   /* private String profileImage;*/

}
