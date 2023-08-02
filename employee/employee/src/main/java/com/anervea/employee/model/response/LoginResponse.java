package com.anervea.employee.model.response;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
public class LoginResponse {
    private Long Id;//employeeId
    private String token;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String zipCode;
    private LocalDate dateOfBirth;
    private String gender;
    private Long corporateId;
    private String secret;
    private String profilePicture;
    private Boolean isLoggedIn=true;

}
