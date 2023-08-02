package com.anervea.employee.model.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordReuquest {


    private String oldPassword;
    private String password;


}
